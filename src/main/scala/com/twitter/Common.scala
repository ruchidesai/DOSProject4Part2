package com.twitter

import spray.routing.RequestContext

case class Ping(requestContext: RequestContext)
case class Pong(requestContext: RequestContext)