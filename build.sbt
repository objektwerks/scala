name := "objektwerks.scala"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.0",
  "org.scala-lang.modules" % "scala-async_2.11" % "0.9.2",
  "com.typesafe.akka" % "akka-actor_2.11" % "2.3.4",
  "com.h2database" % "h2" % "1.4.180",
  "com.typesafe.slick" % "slick_2.11" % "2.1.0-M2",
  "org.scalaz" % "scalaz-core" % "7.0.6",
  "org.slf4j" % "slf4j-nop" % "1.6.4")

logLevel := Level.Info

scalacOptions ++= Seq("-unchecked", "-deprecation")
