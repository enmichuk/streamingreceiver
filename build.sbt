name := "streamingreceiver"

version := "1.0"

scalaVersion := "2.11.8"

val sparkV = "2.0.1"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkV,
  "org.apache.spark" %% "spark-streaming" % sparkV,
  "log4j" % "log4j" % "1.2.17",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)