package main

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamReceiver extends LocalSpark{

  def main(args: Array[String]) {
    implicit val ssc = new StreamingContext(sc, Seconds(Configuration.batchInterval))
    runProgram(Configuration.host, Configuration.port)
  }

  def runProgram(host: String, port: Int)(implicit ssc: StreamingContext) = {
    val lines = ssc.socketTextStream(host, port)
    val (events, unparsedLogEvents) = LogParser.parse(lines)
    Aggregator.count(events, unparsedLogEvents)
    ssc.start()
    ssc.awaitTermination()
  }

}

trait LocalSpark {
  val sc = new SparkConf().setMaster("local[*]").setAppName("Stream Receiver")
}
