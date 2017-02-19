package main

import java.io.IOException
import java.net.{ServerSocket, SocketException}

import grizzled.slf4j.Logging

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

class Server(port: Int) extends Thread("Server Thread") with Logging {
  private var serverSocket: ServerSocket = _
  private val threads = ListBuffer[SocketThread]()

  override def run(): Unit = {
    Try(new ServerSocket(port)) match {
      case Success(socket) =>
        serverSocket = socket
        receiveConnections()
      case Failure(e) => error("Error while opening server socket", e)
    }
  }

  private def receiveConnections(): Unit = {
    while (!Thread.currentThread().isInterrupted) {
      try {
        val socket = serverSocket.accept()
        val thread = new SocketThread(socket) {
          override def getMessage: String = "my-host 127.0.0.1 2016-01-01T12:00:00.000 /resource 200"
        }
        threads += thread
        thread.setName(s"SocketThread_${thread.hashCode()}")
        thread.start()
      } catch {
        case e: SocketException => info(e)
        case e: IOException => error("Error while accepting socket connection", e)
      }
    }
    releaseResources()
  }

  private def releaseResources(): Unit = {
    threads.foreach(_.shutdown())
  }

  def shutdown(): Unit = {
    interrupt()
    try {
      serverSocket.close()
    } catch {
      case e: IOException => error("Error while closing server socket", e)
    }
  }
}
