package com.twitter
 
import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http
 
object Main extends App {

    var something = "Hello"
 
    implicit val system = ActorSystem("TwitterSystem")
 
    val sprayserver1 = system.actorOf(Props[SprayServer], "SprayServer1")
	val sprayserver2 = system.actorOf(Props[SprayServer], "SprayServer2")
	val sprayserver3 = system.actorOf(Props[SprayServer], "SprayServer3")
	val sprayserver4 = system.actorOf(Props[SprayServer], "SprayServer4")
	val sprayserver5 = system.actorOf(Props[SprayServer], "SprayServer5")
 
    IO(Http)(system) ! Http.Bind(sprayserver1, "0.0.0.0", port = 8080)
	IO(Http)(system) ! Http.Bind(sprayserver2, "0.0.0.0", port = 8088)
	IO(Http)(system) ! Http.Bind(sprayserver3, "0.0.0.0", port = 8990)
	IO(Http)(system) ! Http.Bind(sprayserver4, "0.0.0.0", port = 8997)
	IO(Http)(system) ! Http.Bind(sprayserver5, "0.0.0.0", port = 8998)
 
    readLine()
    println("Shutting down...")
    system.shutdown()
}