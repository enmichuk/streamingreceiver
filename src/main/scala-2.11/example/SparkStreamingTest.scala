package example

import model.Event
import org.apache.log4j.{Level, Logger}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}

import scala.util.Try

object SparkStreamingTest {

  def countAggregates(events: DStream[Event], unparsedLogEvents: DStream[Try[Event]]) = {
    val unparsedLogEventsCount = unparsedLogEvents.count()
    val aggregateByHosts = events.map(e => (e.host, 1)).reduceByKey(_ + _)
    val aggregateByResources = events.map(e => (e.resourcePath, 1)).reduceByKey(_ + _)
    aggregateByHosts.print()
    aggregateByResources.print()
    unparsedLogEventsCount.print()
  }

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local[*]").setAppName("Streaming Receiver")
    val ssc = new StreamingContext(conf, Seconds(3))
    Logger.getRootLogger().setLevel(Level.ERROR)
    val lines = ssc.socketTextStream("localhost", 9999, StorageLevel.MEMORY_AND_DISK_SER)
    val (events, unparsedLogEvents) = LogParser.parse(lines)
    countAggregates(events, unparsedLogEvents)
    ssc.start()
    ssc.awaitTermination()
  }

}

object LogParser {

  def parse(inputLogEvents:  InputDStream[String]) = {
    val tryEvents = inputLogEvents.map(logEvent => Try(Event(logEvent)))
    val unparsedLogEvents = tryEvents.filter(_.isFailure)
    val events = tryEvents.filter(_.isSuccess).map(_.get)
    (events, unparsedLogEvents)
  }

}
