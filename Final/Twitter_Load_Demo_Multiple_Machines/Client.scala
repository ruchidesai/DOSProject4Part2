import akka.actor._
import common._
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._
import scala.collection.mutable.Queue
import scala.util.Random

object Twitter {
  
  class Client(no_of_actors: Int, client_no: Int, ip_address: String) extends Actor {
    var actorArray = Array.ofDim[ActorRef](no_of_actors)
	val factor = 2 * (client_no - 1) * 10
	val remote_server = context.actorFor("akka.tcp://TwitterServer@" + ip_address + ":2552/user/master_server")
    remote_server! IAmAlive	
	
	def receive = {
	  case `CreateUsers` =>
	    for(i <- 0 until no_of_actors) {
          actorArray(i) = context.actorOf(Props(new User(no_of_actors, ip_address, client_no)), name = (factor + i).toString)
        }
		
	  case `Stop` =>
	    println("SHUTTING DOWN...")	
        context.system.shutdown
	}
  }
  
  class User(no_of_actors: Int, ip_address : String, client_no: Int) extends Actor {
    val remote_server = context.actorFor("akka.tcp://TwitterServer@" + ip_address + ":2552/user/master_server/server" + client_no.toString)
	var text = "sdajkhfjk sdh fhjkewhfjkd, fk35s3fhj kwhkj787 7286GHG7 67gd2uye73, 67dg675 dajdhjhd 354 56546aD KJO3 7878HJhdl k4545fsa."
	
	var tweet_after_every = 0
	var check_home_page_after_every = 0
	var check_mentions_feed_after_every = 0
	var self_id = self.path.name.toInt
	
	if(self_id >= 0 && self_id < 5000) {
	  tweet_after_every = 20
	  check_home_page_after_every = 1000
	  check_mentions_feed_after_every = -1
	}
    else if(self_id >= 5000 && self_id < 19250) {
 	  tweet_after_every = 180
	  check_home_page_after_every = 40
	  check_mentions_feed_after_every = 1000
	}
	else if(self_id >= 19250 && self_id < 80000) {
	  tweet_after_every = -1
	  check_home_page_after_every = 20
	  check_mentions_feed_after_every = 2000
	}
	
	else {
	  tweet_after_every = -1
	  check_home_page_after_every = -1
	  check_mentions_feed_after_every = -1
	}
	
	override def preStart() {
	  import context.dispatcher
      if(tweet_after_every > -1)	    
	    context.system.scheduler.schedule(30 milliseconds, tweet_after_every seconds, self, SendTweet)
	  if(check_home_page_after_every > -1)	    
	    context.system.scheduler.schedule(30 milliseconds, check_home_page_after_every seconds, remote_server, HomePage)
	  if(check_mentions_feed_after_every > -1)	    
	    context.system.scheduler.schedule(30 milliseconds, check_mentions_feed_after_every seconds, remote_server, MentionsFeed)
	}
	
	def receive = {
	  case `SendTweet` =>
	    var rand = new Random()
        var tweet_type = rand.nextInt(3).abs
		var id = self.path.name.toInt
		var name = 0
		if(tweet_type != 0)
		  name = rand.nextInt(no_of_actors).abs //ensure that it is not its own name
		remote_server ! Tweet(id, tweet_type, name, text)
		
	  case _ =>
	    //default case
	}
  }
  
  def main(args: Array[String]) {
    if(args.length < 3)
	  println("usage: scala Client.scala <no_of_actors> <ip_address> <client_no>")
	else {
	  val myConfig = ConfigFactory.parseString("""
        akka {
		  stdout-loglevel = "OFF"
          loglevel = "OFF"
          actor {
            provider = "akka.remote.RemoteActorRefProvider"
            serializers {
              java = "akka.serialization.JavaSerializer"
              proto = "akka.remote.serialization.ProtobufSerializer"
            }
            serialization-bindings {
              "java.lang.String" = java
              "com.google.protobuf.Message" = proto
            }
          }
          remote {
            transport = "akka.remote.netty.NettyRemoteTransport"
            netty {
              hostname = "127.0.0.1"
              port = 2552
            }
          }
        }
      """)
      val no_of_actors = args(0).toInt
	  val ip_address = args(1)
	  val client_no = args(2).toInt
	  val system = ActorSystem("TwitterClient" + client_no.toString, ConfigFactory.load(myConfig))
	  val client = system.actorOf(Props(new Client(no_of_actors, client_no, ip_address)), name = "client" + client_no.toString)
	  
	}
  }
}
		  