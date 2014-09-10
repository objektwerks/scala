name := "objektwerks.scala"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq (
  "org.scala-lang.modules" % "scala-async_2.11" % "0.9.2",
  "com.typesafe.akka" % "akka-actor_2.11" % "2.3.4",
  "com.h2database" % "h2" % "1.4.180",
  "com.typesafe.slick" % "slick_2.11" % "2.1.0-M2",
  "org.scalaz" %% "scalaz-core" % "7.1.0",
  "org.scalafx" % "scalafx_2.11" % "8.0.5-R5",
  "org.json4s" % "json4s-jackson_2.11" % "3.2.10",
  "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test"
)

logLevel := Level.Info

scalacOptions ++= Seq("-unchecked", "-deprecation")
