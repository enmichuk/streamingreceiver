package main

import com.typesafe.config.ConfigFactory

object Configuration {

  private val config = ConfigFactory.load().getConfig("config")
  val host = config.getString("host")
  val port = config.getInt("port")
  val batchInterval = config.getInt("batchInterval")

}
