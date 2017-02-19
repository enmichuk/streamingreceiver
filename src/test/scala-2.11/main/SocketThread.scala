package main

import java.io.{IOException, PrintStream}
import java.net.Socket

import grizzled.slf4j.Logging

abstract class SocketThread(socket: Socket) extends Thread with Logging {
  override def run(): Unit = {
    val out = new PrintStream(socket.getOutputStream)
    while (!Thread.currentThread().isInterrupted) {
      out.println(getMessage)
      out.flush()
    }
    out.close()
    releaseSocket()
  }

  private def releaseSocket(): Unit ={
    try {
      socket.close()
    } catch {
      case e: IOException => error("Error while closing socket connection", e)
    }
  }

  def shutdown(): Unit = {
    interrupt()
  }

  protected def getMessage: String
}
