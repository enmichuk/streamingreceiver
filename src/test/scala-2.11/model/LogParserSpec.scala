package model

import java.text.SimpleDateFormat
import java.util.Date

import org.scalacheck.Gen
import org.scalacheck.Gen._
import org.scalatest.prop.PropertyChecks
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

class LogParserSpec
  extends FlatSpec
  with PropertyChecks
  with Matchers
  with BeforeAndAfterEach {

  val simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss.sss")

  override def beforeEach() = {
  }

  override def afterEach() = {
  }

  val logGen: Gen[String] =
    for{
      host <- listOfN(10, Gen.alphaChar).map(_.mkString)
      ip <- listOfN(10, Gen.alphaChar).map(_.mkString)
      resource <- listOfN(10, Gen.alphaChar).map(_.mkString)
    } yield host + " " + ip + " " + simpleDateFormat.format(new Date()) + " " + resource + " " + 200

  val logsGen = listOfN(1000, logGen)

  it should "Number of  events cannot change during computation" in {

  }

}

