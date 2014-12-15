package com.twitter

import akka.actor._
import spray.http.{HttpMethods, HttpRequest, Uri, HttpResponse}
import spray.routing.RequestContext
import spray.httpx.SprayJsonSupport._
import spray.json._
import DefaultJsonProtocol._

class ServerActor extends Actor {

  def receive = {
    case Tweet(id, tweet_type, name, msg, requestContext) =>
	  println("Before inserting home_pages(id).length is ")
      println(Main.home_pages(id).length)
	  Main.home_pages(id) += msg
	  tweet_type match {
		  case 0 =>
		    if(Main.follower_mapping(id).size > 0) {
			  for(j <- Main.follower_mapping(id)) {
			    Main.home_pages(j) += msg
			    if(Main.home_pages(j).size > 100)
			      Main.home_pages(j).drop(Main.home_pages(j).size - 100)
			  }
			}
				
		  case 1 =>
			var common_followers = Main.follower_mapping(id).intersect(Main.follower_mapping(name))
			if(common_followers.size > 0) {
			  for(j <- common_followers) {
			    Main.home_pages(j) += msg
			    if(Main.home_pages(j).size > 100)
			      Main.home_pages(j).drop(Main.home_pages(j).size - 100)
			  }
              Main.mentions_feeds(name) += msg
			  if(Main.mentions_feeds(name).size > 50)
			    Main.mentions_feeds(name).drop(Main.mentions_feeds(name).size - 50)
			}

          case 2 =>
		    if(Main.follower_mapping(id).size > 0) {
			  for(j <- Main.follower_mapping(id)) {
			    Main.home_pages(j) += msg
			    if(Main.home_pages(j).size > 100)
			      Main.home_pages(j).drop(Main.home_pages(j).size - 100)
			  }			
			  Main.mentions_feeds(name) += msg
			  if(Main.mentions_feeds(name).size > 50)
			    Main.mentions_feeds(name).drop(Main.mentions_feeds(name).size - 50)
			}
		}
	  println("After inserting home_pages(id).length is ")
	  println(Main.home_pages(id).length)
	  requestContext.complete(HttpResponse(entity = "Done"))
	
	case HomePage(id, requestContext) =>
	  var reply = Main.home_pages(id)
	  println("home_pages(id).length = ")
	  println(Main.home_pages(id).length)
	  var json_reply = ""
	  for(item <- reply) {
	    println("printing....")
	    println(item.toJson)
	    json_reply += item.toJson
	  }
	  println(json_reply)
	  requestContext.complete(HttpResponse(entity = json_reply))
	  
	case MentionsFeed(id, requestContext) =>
	  var reply = Main.mentions_feeds(id)
	  var json_reply = ""
	  for(item <- reply) {
	    json_reply += item.toJson
	  }
	  requestContext.complete(HttpResponse(entity = json_reply))
  }
}