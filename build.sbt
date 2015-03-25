name := "objektwerks.scala"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq (
  "com.typesafe" % "config" % "1.2.1",
  "org.scala-lang.modules" % "scala-async_2.11" % "0.9.2",
  "com.typesafe.akka" % "akka-actor_2.11" % "2.3.9",
  "com.h2database" % "h2" % "1.4.186",
  "com.typesafe.slick" % "slick_2.11" % "2.1.0",
  "org.scalaz" %% "scalaz-core" % "7.1.1",
  "org.scalafx" % "scalafx_2.11" % "8.0.40-R8",
  "net.databinder.dispatch" % "dispatch-core_2.11" % "0.11.2",
  "org.json4s" % "json4s-jackson_2.11" % "3.2.11",
  "org.apache.spark" % "spark-core_2.11" % "1.3.0",
  "org.apache.spark" % "spark-streaming_2.11" % "1.3.0",
  "org.slf4j" % "slf4j-api" % "1.7.10",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "org.scalacheck" % "scalacheck_2.11" % "1.12.2" % "test",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
)

logLevel := Level.Info
