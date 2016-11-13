package main

import model.Event
import org.apache.spark.streaming.dstream.DStream

import scala.util.Try

object Aggregator {

  def count(events: DStream[Event], unparsedLogEvents: DStream[Try[Event]]) = {
    val unparsedLogEventsCount = unparsedLogEvents.count()
    val aggregateByHosts = events.map(e => (e.host, 1)).reduceByKey(_ + _)
    val aggregateByResources = events.map(e => (e.resourcePath, 1)).reduceByKey(_ + _)
    aggregateByHosts.print()
    aggregateByResources.print()
    unparsedLogEventsCount.print()
  }

}
