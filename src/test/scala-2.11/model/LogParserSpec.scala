package model

import java.text.SimpleDateFormat
import java.util.Date

import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop.forAll
import org.scalacheck.Gen._

object LogParserSpec extends Properties("Log Parser") {

  val simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss.sss")

  val log: Gen[String] =
    for{
      host <- listOfN(10, Gen.alphaChar).map(_.mkString)
      ip <- listOfN(10, Gen.alphaChar).map(_.mkString)
      resource <- listOfN(10, Gen.alphaChar).map(_.mkString)
    } yield host + " " + ip + " " + simpleDateFormat.format(new Date()) + " " + resource + " " + 200

  val logs = listOf(log)

  property("startsWith") = forAll(logs) { (logs_1: List[String]) =>
    true
  }


}
