package main

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamReceiver {

  def main(args: Array[String]) {
    val sc = new SparkConf()
      .setMaster("local[*]")
      .setAppName("Stream Receiver")
    val ssc = new StreamingContext(sc, Seconds(Configuration.batchInterval))
    val lines = ssc.socketTextStream(Configuration.host, Configuration.port)
    lines.print()
    val (events, unparsedLogEvents) = LogParser.parse(lines)
    Aggregator.count(events, unparsedLogEvents)
    ssc.start()
    ssc.awaitTermination()
  }

}
