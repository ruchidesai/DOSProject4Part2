package com.twitter

import akka.actor._
import spray.http.{HttpMethods, HttpRequest, Uri, HttpResponse}
import spray.routing.RequestContext

class ServerActor extends Actor {

  def receive = {
    case Ping(requestContext) =>
	  requestContext.complete(HttpResponse(entity = "Pong"))
	
	case Pong(requestContext) =>
	  requestContext.complete(HttpResponse(entity = "Ping"))
  }
}