package com.twitter

import akka.actor._
import spray.http.{HttpMethods, HttpRequest, Uri, HttpResponse}
import spray.routing.RequestContext

class ServerActor extends Actor {

  def receive = {
    case Tweet(id, tweet_type, name, msg, requestContext) =>
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
		requestContext.complete(HttpResponse(entity = "Done"))
	
	case HomePage(id, requestContext) =>
	  //home_pages(id)
	  requestContext.complete(HttpResponse(entity = id.toString))
	  
	case MentionsFeed(id, requestContext) =>
	  //mentions_feeds(id)
	  requestContext.complete(HttpResponse(entity = id.toString))
  }
}