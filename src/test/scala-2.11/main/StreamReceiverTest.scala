package main

import grizzled.slf4j.Logging
import org.scalatest._
import org.scalatest.concurrent.Eventually

class StreamReceiverTest extends FlatSpec with Eventually with Matchers with Logging
  with BeforeAndAfterAllConfigMap {

  var server: Server = _

  override protected def beforeAll(configMap: ConfigMap): Unit = {
    debug("Start initialize environment")
    server = new Server(Configuration.port)
    server.start()
    debug("Environment is initialized")
  }

  override protected def afterAll(configMap: ConfigMap): Unit = {
    debug("Start release resources")
    server.shutdown()
    debug("Resources released")
  }

  "StreamReceiverTest" should "work correctly" in {
//    StreamReceiver.main(Array())
  }
}
