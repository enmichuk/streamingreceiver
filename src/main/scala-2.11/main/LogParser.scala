package main

import model.Event
import org.apache.spark.streaming.dstream.InputDStream

import scala.util.Try

object LogParser {

  def parse(inputLogEvents:  InputDStream[String]) = {
    val tryEvents = inputLogEvents.map(logEvent => Try(Event(logEvent)))
    val unparsedLogEvents = tryEvents.filter(_.isFailure)
    val events = tryEvents.filter(_.isSuccess).map(_.get)
    (events, unparsedLogEvents)
  }

}
