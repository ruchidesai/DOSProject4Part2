package com.twitter

import spray.json._

class HomePageEntry(ts: Long, tid: Int, msg: String) {
  var time_stamp = ts
  var tweeter_id = tid
  var message = msg
}

class MentionFeedEntry(ts: Long, tid: Int, msg: String) {
  var time_stamp = ts
  var tweeter_id = tid
  var message = msg
}

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit object HomePageEntryJsonFormat extends RootJsonFormat[HomePageEntry] {
    def write(hpe: HomePageEntry) = JsObject(
      "time_stamp" -> JsNumber(hpe.time_stamp),
      "tweeter_id" -> JsNumber(hpe.tweeter_id),
      "message" -> JsString(hpe.message)
    )
    def read(value: JsValue) = {
      value.asJsObject.getFields("time_stamp", "tweeter_id", "message") match {
        case Seq(JsString(time_stamp), JsNumber(tweeter_id), JsNumber(message)) =>
          new HomePageEntry(time_stamp.toLong, tweeter_id.toInt, message.toString)
        case _ => throw new DeserializationException("HomePageEntry expected")
      }
    }
  }
  
  implicit object MentionFeedEntryJsonFormat extends RootJsonFormat[MentionFeedEntry] {
    def write(hpe: MentionFeedEntry) = JsObject(
      "time_stamp" -> JsNumber(hpe.time_stamp),
      "mentioned_by" -> JsNumber(hpe.tweeter_id),
      "message" -> JsString(hpe.message)
    )
    def read(value: JsValue) = {
      value.asJsObject.getFields("time_stamp", "tweeter_id", "message") match {
        case Seq(JsString(time_stamp), JsNumber(tweeter_id), JsNumber(message)) =>
          new MentionFeedEntry(time_stamp.toLong, tweeter_id.toInt, message.toString)
        case _ => throw new DeserializationException("MentionFeedEntry expected")
      }
    }
  }
}