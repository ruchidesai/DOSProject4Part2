package common

import scala.collection.mutable.Queue

sealed trait Message
case class Start(ip_addresses: Array[String]) extends Message
case class Tweet(id: Int, tweet_type: Int, name: Int, msg: String) extends Message
case object HomePage extends Message
case object MentionsFeed extends Message
case object Stop extends Message
case object Print extends Message
case object CreateUsers extends Message
case object SendTweet extends Message
case object IAmAlive extends Message
case class DisplayHomePage(home_page: Queue[String]) extends Message
case class DisplayMentionFeed(mention_feed: Queue[String]) extends Message