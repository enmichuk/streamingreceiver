package model

import java.text.SimpleDateFormat
import java.util.Date

import scala.util.{Failure, Success, Try}

case class Event(host: String, ip: String, date: Date, path: String, status: Int)

object Event {
  def apply(event: String): Event = {
    event.split(" ").toList match {
      case List(host, ip, date, path, status) => Event(host, ip, DateParser(date), path, IntParser(status))
      case _ => throw EventDeserializationException("not valid format for string: " + event)
    }
  }
}

case class EventDeserializationException(message: String) extends Exception(message)

object DateParser {
  def apply(date: String): Date = {
    Try(new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss.sss").parse(date)) match {
      case Success(parsedDate) => parsedDate
      case Failure(e) =>
        throw EventDeserializationException("not valid format for date string: " + date + ". Cause: " + e.getMessage)
    }
  }
}

object IntParser {
  def apply(number: String): Int = {
    Try(number.toInt) match {
      case Success(parsedNumber) => parsedNumber
      case Failure(e) =>
        throw EventDeserializationException("not valid format for integer string: " + number + ". Cause: " + e.getMessage)
    }
  }
}