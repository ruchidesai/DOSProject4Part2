package com.twitter
 
import akka.actor.{Actor, Props}
import spray.can.Http
import spray.http.{HttpMethods, HttpRequest, Uri, HttpResponse}
import HttpMethods._
import spray.routing._
import akka.routing.RoundRobinRouter

class SprayServer extends Actor with SprayService{

  def actorRefFactory = context  
  
  def receive = runRoute(sprayTwitterRoute)
 
}

trait SprayService extends HttpService {
  val serveractor = actorRefFactory.actorOf(Props[ServerActor].withRouter(RoundRobinRouter(nrOfInstances = 50000)), name = "serverrouter")
  val sprayTwitterRoute =
    path("tweet" / IntNumber / IntNumber / IntNumber / Segment) { (id, tweet_type, name, msg) =>
	  requestContext =>
	    serveractor ! Tweet(id, tweet_type, name, msg, requestContext)
    } ~
	path("homepage" / IntNumber) { (id) =>
	  requestContext =>
	    serveractor ! HomePage(id, requestContext)
	} ~
	path("mentionsfeed" /IntNumber) { (id) =>
	  requestContext =>
	    serveractor ! MentionsFeed(id, requestContext)
	} ~
	path("followers" / IntNumber) { (id) =>
	  requestContext =>
	    serveractor ! GetFollowers(id, requestContext)
	} ~
	path("getTweet" / IntNumber) { (id) =>
	  requestContext =>
	    serveractor ! HomePage(id, requestContext)
	}
}