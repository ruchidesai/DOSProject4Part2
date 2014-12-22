package com.twitter

import akka.actor._
import spray.http.{HttpMethods, HttpRequest, Uri, HttpResponse}
import spray.routing.RequestContext
import spray.httpx.SprayJsonSupport._
import spray.json._
import MyJsonProtocol._

class ServerActor extends Actor {

  def receive = {
    case Tweet(id, tweet_type, name, msg, requestContext) =>
	  val time_stamp = System.currentTimeMillis
	  val home_page_entry = new HomePageEntry(time_stamp, id, msg)
	  Main.home_pages(id) = Main.home_pages(id) :+ home_page_entry
	  tweet_type match {
		  case 0 =>
		    if(Main.follower_mapping(id).size > 0) {
			  for(j <- Main.follower_mapping(id)) {
			    Main.home_pages(j) :+= home_page_entry
			    if(Main.home_pages(j).size >= 100) {
			      Main.home_pages(j).drop(Main.home_pages(j).size - 100)
				}
			  }
			}
				
		  case 1 =>
		    val mention_feed_entry = new MentionFeedEntry(time_stamp, id, msg)
		    Main.mentions_feeds(name) :+= mention_feed_entry
			var common_followers = Main.follower_mapping(id).intersect(Main.follower_mapping(name))
			if(common_followers.size > 0) {
			  for(j <- common_followers) {
			    Main.home_pages(j) :+= home_page_entry
			    if(Main.home_pages(j).size >= 100)
			      Main.home_pages(j).drop(Main.home_pages(j).size - 100)
			  }              
			  if(Main.mentions_feeds(name).size >= 50)
			    Main.mentions_feeds(name).drop(Main.mentions_feeds(name).size - 50)
			}

          case 2 =>
		    val mention_feed_entry = new MentionFeedEntry(time_stamp, id, msg)
		    if(Main.follower_mapping(id).size > 0) {
			  for(j <- Main.follower_mapping(id)) {
			    Main.home_pages(j) :+= home_page_entry
			    if(Main.home_pages(j).size >= 100)
			      Main.home_pages(j).drop(Main.home_pages(j).size - 100)
			  }			
			  Main.mentions_feeds(name) :+= mention_feed_entry
			  if(Main.mentions_feeds(name).size >= 50)
			    Main.mentions_feeds(name).drop(Main.mentions_feeds(name).size - 50)
			}
		}
	  var json_tweet = ""
	  json_tweet += home_page_entry.toJson
	  requestContext.complete(HttpResponse(entity = json_tweet))
	
	case HomePage(id, requestContext) =>
	  var reply = Main.home_pages(id)
	  var json_reply = ""
	  if(reply.length == 0)
	    json_reply = "No activity yet."
	  else {
	    for(item <- reply) {
	      json_reply += "\n" + item.toJson
	    }
	  }
	  requestContext.complete(HttpResponse(entity = json_reply))
	  
	case MentionsFeed(id, requestContext) =>
	  var reply = Main.mentions_feeds(id)
	  var json_reply = ""
	  if(reply.length == 0)
	    json_reply = "No mentions yet."
	  else {
	    for(item <- reply) {
	      json_reply += "\n" + item.toJson
	    }
	  }
	  requestContext.complete(HttpResponse(entity = json_reply))
	  
	case GetFollowers(id, requestContext) =>
	  var reply = Main.follower_mapping(id)
	  var json_reply = ""
	  if(reply == null)
	    json_reply = "No followers yet."
	  else
	    json_reply += reply.toJson
	  requestContext.complete(HttpResponse(entity = json_reply))
  }
}