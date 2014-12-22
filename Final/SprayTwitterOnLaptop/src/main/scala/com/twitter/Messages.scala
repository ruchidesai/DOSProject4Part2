package com.twitter

import spray.routing.RequestContext

case class Tweet(id: Int, tweet_type: Int, name: Int, msg: String, requestContext: RequestContext)
case class HomePage(id: Int, requestContext: RequestContext)
case class MentionsFeed(id: Int, requestContext: RequestContext)
case class GetFollowers(id: Int, requestContext: RequestContext)