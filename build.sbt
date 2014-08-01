name := "objektwerks.scala"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.0",
  "org.scala-lang.modules" % "scala-async_2.11" % "0.9.2",
  "com.typesafe.akka" % "akka-actor_2.11" % "2.3.4",
  "com.h2database" % "h2" % "1.3.173")

logLevel := Level.Info

scalacOptions ++= Seq("-unchecked", "-deprecation")
