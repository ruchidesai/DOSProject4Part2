import akka.actor._
import common._
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._
import scala.collection.mutable.Queue
import scala.util.Random

object Twitter {
  var tweets_received = 0
  var messages_delivered = 0
  
  var follower_mapping = Array[List[Int]]()	
  var home_pages = Array[Queue[String]]()
  var mentions_feeds = Array[Queue[String]]()
  
  
  def main(args: Array[String]) {
    if(args.length < 6)
	  println("usage: scala Server.scala <no_of_actors> <ip_address>")
	else {
	  val no_of_actors = args(0).toInt
	  
	  var ip_addresses = new Array[String](5)
	  for(i <- 0 until 5)
	    ip_addresses(i) = args(i+1)
		
	  print("Creating user mapping...")
	  
	  follower_mapping = new Array[List[Int]](no_of_actors)
	  
	  follower_mapping(0) = for(i <- List.range(500, 3500)) yield i
      follower_mapping(1) = for(i <- List.range(3501, 5501)) yield i

      for(i <-5501 until 5791)
        follower_mapping(i) = for(j <- List.range(5501, 100000) if (j % 94 == 0 && j != i)) yield j
	  print("...")
  
      for(i <- 5791 until 6191)
        follower_mapping(i) = for(j <- List.range(5501, 100000) if (j % 189 == 0 && j != i)) yield j
	  print("...")
  
      for(i <- 6191 until 6591)
        follower_mapping(i) = for(j <- List.range(5501, 100000) if (j % 630 == 0 && j != i)) yield j
	  print("...")
  
      for(i <- 6591 until 8541)
        follower_mapping(i) = for(j <- List.range(5501, 100000) if (j % 945 == 0 && j != i)) yield j
	  print("...")
  
      for(i <- 8541 until 10491)
        follower_mapping(i) = for(j <- List.range(5501, 100000) if (j % 1890 == 0 && j != i)) yield j
	  print("...")
  
      for(i <- 10491 until 19491)
        follower_mapping(i) = for(j <- List.range(5501, 100000) if (j % 9450 == 0 && j != i)) yield j
	  print("...")
  
      for(i <- 19491 until 25491)
        follower_mapping(i) = for(j <- List.range(5501, 100000) if (j % 18900 == 0 && j != i)) yield j
	  print("...")
  
      var start = 81000
	  var end = 99999
	  var num = 0
	  var rand = new Random()
	  for(i <- 25491 until 99999) {
	    num = start + rand.nextInt(end - start + 1)
        follower_mapping(i) = List(num)
	  }
	  print("...")
	
	  for(i <- 2 until 5500) {
	    num = start + rand.nextInt(end - start + 1)
        follower_mapping(i) = List(num)
	  }
	  print("...")
	  
	  home_pages = new Array[Queue[String]](no_of_actors)
	  for(i <- 0 until no_of_actors)
	    home_pages(i) = new Queue[String]
	  print("...")
		
	  mentions_feeds = new Array[Queue[String]](no_of_actors)
	  for(i <- 0 until (no_of_actors))
	    mentions_feeds(i) = new Queue[String]
	  print("...")
	  println("Done")
	  
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
	
	  val system = ActorSystem("TwitterServer", ConfigFactory.load(myConfig))
	  val master_server = system.actorOf(Props(new MasterServer(no_of_actors)), name="master_server")	
	  master_server ! Start(ip_addresses)
	}
  }
  
  class MasterServer(no_of_actors: Int) extends Actor {	
	var actorArray = Array.ofDim[ActorRef](5)
	var ip_addresses = new Array[String](5)
	var countmsg = 0
	
	override def preStart() {
      import context.dispatcher
      context.system.scheduler.scheduleOnce(5 minutes, self, Stop)   
	  context.system.scheduler.schedule(30 seconds, 3 seconds, self, Print)
    }
	
	def receive = {
	  case Start(ips) =>
	    ip_addresses = ips
		for(i <- 0 until 5)
		  actorArray(i) = context.actorOf(Props[Server], name = ("server" + (i+1).toString))
	    println("Server is ready")
		
	  case `IAmAlive` =>
	    countmsg += 1
		println("Client ping received")
		if(countmsg >= 5) {
		  println("All clients are ready")
		  for(i <- 1 to 5) {
		    context.actorFor("akka.tcp://TwitterClient" + i.toString + "@" + ip_addresses(i-1) + ":2552/user/client" + i.toString) ! CreateUsers
		  }	
		}
		
	  case `Print` =>
	    println("Number of tweets received = " + tweets_received)
		println("Number of messages delivered = " + messages_delivered)
		println()
		
	  case `Stop` =>
	    println("Number of tweets received = " + tweets_received)
		println("Number of messages delivered = " + messages_delivered)
		for(i <- 1 to 5) {
		  //println("akka.tcp://TwitterClient" + i.toString + "@" + ip_addresses(i-1) + ":2552/user/client" + i.toString)
          context.actorFor("akka.tcp://TwitterClient" + i.toString + "@" + ip_addresses(i-1) + ":2552/user/client" + i.toString) ! Stop
		}
		context.system.shutdown
	}
  }
  
  class Server extends Actor {
    def receive = {
	  case Tweet(id, tweet_type, name, msg) =>
	    tweets_received += 1
	    tweet_type match {
		  case 0 =>
		    if(follower_mapping(id).size > 0) {
			  for(j <- follower_mapping(id)) {
			    home_pages(j) += msg
			    if(home_pages(j).size > 100)
			      home_pages(j).drop(home_pages(j).size - 100)
			  }
			}
				
		  case 1 =>
			var common_followers = follower_mapping(id).intersect(follower_mapping(name))
			if(common_followers.size > 0) {
			  for(j <- common_followers) {
			    home_pages(j) += msg
			    if(home_pages(j).size > 100)
			      home_pages(j).drop(home_pages(j).size - 100)
			  }
              mentions_feeds(name) += msg
			  if(mentions_feeds(name).size > 50)
			    mentions_feeds(name).drop(mentions_feeds(name).size - 50)
			}

          case 2 =>
		    if(follower_mapping(id).size > 0) {
			  for(j <- follower_mapping(id)) {
			    home_pages(j) += msg
			    if(home_pages(j).size > 100)
			      home_pages(j).drop(home_pages(j).size - 100)
			  }			
			  mentions_feeds(name) += msg
			  if(mentions_feeds(name).size > 50)
			    mentions_feeds(name).drop(mentions_feeds(name).size - 50)
			}
		}
			
	  case `HomePage` =>
	    messages_delivered += 1
	    var id = sender.path.name.toInt
		//sender ! DisplayHomePage(home_pages(id))

      case `MentionsFeed` =>
	    messages_delivered += 1
        var id = sender.path.name.toInt
		//sender ! DisplayMentionFeed(mentions_feeds(id))	  
	}
  }
}
	