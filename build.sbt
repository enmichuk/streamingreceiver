name := "streamingreceiver"

version := "1.0"

scalaVersion := "2.11.8"

val sparkV = "2.1.0"
val slf4jV = "1.7.5"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkV,
  "org.apache.spark" %% "spark-streaming" % sparkV,
  "org.slf4j" % "slf4j-api" % slf4jV,
  "org.clapper" %% "grizzled-slf4j" % "1.2.0",
  "com.typesafe" % "config" % "1.3.1",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"
)