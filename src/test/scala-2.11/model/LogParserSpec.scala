package model

import java.text.SimpleDateFormat
import java.util.Date

import main.{Configuration, LocalSpark, LogParser}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.scalacheck.Gen
import org.scalacheck.Gen._
import org.scalatest.prop.PropertyChecks
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

import scala.collection.mutable

class LogParserSpec
  extends FlatSpec
  with PropertyChecks
  with Matchers
  with BeforeAndAfterEach
  with LocalSpark{

  val simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss.sss")
  private var ssc: StreamingContext = _

  override def beforeEach() = {
    ssc = new StreamingContext(sc, Seconds(Configuration.batchInterval))
  }

  override def afterEach() = {
    ssc.stop()
  }

  val logGen: Gen[String] =
    for{
      host <- listOfN(10, Gen.alphaChar).map(_.mkString)
      ip <- listOfN(10, Gen.alphaChar).map(_.mkString)
      resource <- listOfN(10, Gen.alphaChar).map(_.mkString)
    } yield host + " " + ip + " " + simpleDateFormat.format(new Date()) + " " + resource + " " + 200

  val logsGen = listOf(logGen)

  it should "this test in incomplete" in {
    forAll(logsGen) { (logs: List[String]) =>
      val logsStream = ssc.sparkContext.parallelize(logs)
      val lines = ssc.queueStream[String](mutable.Queue(logsStream))
      val (events, unparsedLogEvents) = LogParser.parse(lines)
      List() shouldBe empty
    }
  }

}

