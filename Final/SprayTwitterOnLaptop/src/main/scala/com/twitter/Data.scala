package com.twitter

import scala.collection.mutable.Queue
import scala.util.Random

object OurData {
  
  def create_follower_mappings(no_of_actors: Int): Array[List[Int]] = {
    println("Creating user mapping...")
    var follower_mapping = new Array[List[Int]](no_of_actors)
	
	follower_mapping(0) = for(i <- List.range((0.005*no_of_actors).toInt, (0.035*no_of_actors).toInt)) yield i
    follower_mapping(1) = for(i <- List.range((0.03501*no_of_actors).toInt, (0.05501*no_of_actors).toInt)) yield i
	
	for(i <-(0.05501*no_of_actors).toInt until (0.05791*no_of_actors).toInt)
      follower_mapping(i) = for(j <- List.range((0.05501*no_of_actors).toInt, no_of_actors) if (j % (0.00094*no_of_actors).toInt == 0 && j != i)) yield j
	println("...")
  
    for(i <- (0.05791*no_of_actors).toInt until (0.06191*no_of_actors).toInt)
      follower_mapping(i) = for(j <- List.range((0.05501*no_of_actors).toInt, no_of_actors) if (j % (0.00189*no_of_actors).toInt == 0 && j != i)) yield j
	println("...")
  
    for(i <- (0.06191*no_of_actors).toInt until (0.06591*no_of_actors).toInt)
      follower_mapping(i) = for(j <- List.range((0.05501*no_of_actors).toInt, no_of_actors) if (j % (0.0063*no_of_actors).toInt == 0 && j != i)) yield j
	println("...")
	
	for(i <- (0.06591*no_of_actors).toInt until (0.08541*no_of_actors).toInt)
      follower_mapping(i) = for(j <- List.range((0.05501*no_of_actors).toInt, no_of_actors) if (j % (0.00945*no_of_actors).toInt == 0 && j != i)) yield j
	println("...")
  
    for(i <- (0.08541*no_of_actors).toInt until (0.10491*no_of_actors).toInt)
      follower_mapping(i) = for(j <- List.range((0.05501*no_of_actors).toInt, no_of_actors) if (j % (0.0189*no_of_actors).toInt == 0 && j != i)) yield j
	println("...")
  
    for(i <- (0.10491*no_of_actors).toInt until (0.19491*no_of_actors).toInt)
      follower_mapping(i) = for(j <- List.range((0.05501*no_of_actors).toInt, no_of_actors) if (j % (0.0945*no_of_actors).toInt == 0 && j != i)) yield j
	println("...")
	
	for(i <- (0.19491*no_of_actors).toInt until (0.25491*no_of_actors).toInt)
      follower_mapping(i) = for(j <- List.range((0.05501*no_of_actors).toInt, no_of_actors) if (j % (0.189*no_of_actors).toInt == 0 && j != i)) yield j
	println("...")
  
    var start = (0.81*no_of_actors).toInt
	var end = (0.99999*no_of_actors).toInt
	var num = 0
	var rand = new Random()
	for(i <- (0.25491*no_of_actors).toInt until (0.99999*no_of_actors).toInt) {
	  num = start + rand.nextInt(end - start + 1)
      follower_mapping(i) = List(num)
	}
	println("...")
	
	for(i <- 2 until (0.05500*no_of_actors).toInt) {
	  num = start + rand.nextInt(end - start + 1)
      follower_mapping(i) = List(num)
	}
	println("...")
	return follower_mapping
  }
  
  def initialize_homepages(no_of_actors: Int): Array[Vector[HomePageEntry]] = {
    var home_pages = new Array[Vector[HomePageEntry]](no_of_actors)
	for(i <- 0 until no_of_actors)
	  home_pages(i) = Vector[HomePageEntry]()
	println("...")
	return home_pages
  }
	
  def initialize_mentionsfeeds(no_of_actors: Int): Array[Vector[MentionFeedEntry]] = {
    var mentions_feeds = new Array[Vector[MentionFeedEntry]](no_of_actors)
	for(i <- 0 until (no_of_actors))
	  mentions_feeds(i) = Vector[MentionFeedEntry]()
	println("...")
	return mentions_feeds
  }
}