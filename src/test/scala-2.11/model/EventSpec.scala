package model

import java.util.Date

import org.scalatest.{FlatSpec, Matchers}

class EventSpec
  extends FlatSpec
  with Matchers {

  //Date parsing
  val date = "2016-01-01T12:00:00.000"
  date should "be valid" in {
    DateParser(date) should be (new Date(1451250000000L))
  }

  //Event parsing
  val inputString = "my-host 127.0.0.1 2016-01-01T12:00:00.000 /resource 200"
  inputString should "produce right Event object" in {
    Event(inputString) should be (Event("my-host", "127.0.0.1", new Date(1451250000000L), "/resource",  200))
  }
  val stringWithFewerAttributes = "my-host 127.0.0.1 2016-01-01T12:00:00.000 /resource"
  it should "throw EventParsingException for input string " + stringWithFewerAttributes in {
    a [EventParsingException] should be thrownBy {
      Event(stringWithFewerAttributes)
    }
  }
  val stringWithWrongDate = "my-host 127.0.0.1 2016.01.01T12:00:00.000 /resource 200"
  it should "throw EventParsingException for input string " + stringWithWrongDate in {
    a [EventParsingException] should be thrownBy {
      Event(stringWithWrongDate)
    }
  }
  val stringWithWrongStatus = "my-host 127.0.0.1 2016-01-01T12:00:00.000 /resource abc"
  it should "throw EventParsingException for input string " + stringWithWrongStatus in {
    a [EventParsingException] should be thrownBy {
      Event(stringWithWrongStatus)
    }
  }

}
