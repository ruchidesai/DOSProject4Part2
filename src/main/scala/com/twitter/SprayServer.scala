package com.twitter
 
import akka.actor.{Actor, Props}
import spray.can.Http
import spray.http.{HttpMethods, HttpRequest, Uri, HttpResponse}
import HttpMethods._
import spray.routing._

class SprayServer extends Actor with SprayService{

  def actorRefFactory = context  
  
  def receive = runRoute(sprayTwitterRoute)
 
  /*def receive = {
    case _:Http.Connected => sender ! Http.Register(self)
 
    case HttpRequest(GET, Uri.Path("/ping"), _, _, _) =>
      sender ! HttpResponse(entity = Main.something)
  }*/
 
}

trait SprayService extends HttpService {
  val serveractor = actorRefFactory.actorOf(Props[ServerActor])
  val sprayTwitterRoute =
    path("ping") {
	  requestContext =>
	    serveractor ! Ping(requestContext)
    } ~
	path("pong") {
	  requestContext =>
	    serveractor ! Pong(requestContext)
	}
}