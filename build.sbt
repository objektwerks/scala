name := "objektwerks.scala"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq (
  "org.scala-lang.modules" % "scala-async_2.11" % "0.9.2",
  "com.typesafe.akka" % "akka-actor_2.11" % "2.3.6",
  "com.h2database" % "h2" % "1.4.181",
  "com.typesafe.slick" % "slick_2.11" % "2.1.0",
  "org.scalaz" %% "scalaz-core" % "7.1.0",
  "org.scalafx" % "scalafx_2.11" % "8.0.20-R6",
  "net.databinder.dispatch" % "dispatch-core_2.11" % "0.11.2",
  "org.json4s" % "json4s-jackson_2.11" % "3.2.10",
  "org.slf4j" % "slf4j-api" % "1.7.7",
  "ch.qos.logback" % "logback-classic" % "1.1.1",
  "org.scalacheck" % "scalacheck_2.11" % "1.11.5" % "test",
  "org.scalatest" % "scalatest_2.11" % "2.2.2" % "test"
)

logLevel := Level.Info

