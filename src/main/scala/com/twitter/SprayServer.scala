package com.twitter
 
import akka.actor.Actor
import spray.can.Http
import spray.http.{HttpMethods, HttpRequest, Uri, HttpResponse}
import HttpMethods._

class SprayServer extends Actor {

  val server = 
 
  def receive = {
    case _:Http.Connected => sender ! Http.Register(self)
 
    case HttpRequest(GET, Uri.Path("/ping"), _, _, _) =>
      sender ! HttpResponse(entity = Main.something)
  }
 
}