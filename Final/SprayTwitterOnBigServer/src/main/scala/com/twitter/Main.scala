package com.twitter
 
import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http
 
object Main extends App {

    val no_of_users = 100000
	val follower_mapping = OurData.create_follower_mappings(no_of_users)
	var home_pages = OurData.initialize_homepages(no_of_users)
	var mentions_feeds = OurData.initialize_mentionsfeeds(no_of_users)
	println("Done")
	println("Server is ready")
 
    implicit val system = ActorSystem("TwitterSystem")
 
    val sprayserver1 = system.actorOf(Props[SprayServer], "SprayServer1")
	//val sprayserver2 = system.actorOf(Props[SprayServer], "SprayServer2")
	//val sprayserver3 = system.actorOf(Props[SprayServer], "SprayServer3")
	//val sprayserver4 = system.actorOf(Props[SprayServer], "SprayServer4")
	//val sprayserver5 = system.actorOf(Props[SprayServer], "SprayServer5")
 
    IO(Http)(system) ! Http.Bind(sprayserver1, "127.0.0.1", port = 8998)
	//IO(Http)(system) ! Http.Bind(sprayserver2, "0.0.0.0", port = 8088)
	//IO(Http)(system) ! Http.Bind(sprayserver3, "0.0.0.0", port = 8990)
	//IO(Http)(system) ! Http.Bind(sprayserver4, "0.0.0.0", port = 8997)
	//IO(Http)(system) ! Http.Bind(sprayserver5, "0.0.0.0", port = 8998)
 
    readLine()
    println("Shutting down...")
    system.shutdown()
}