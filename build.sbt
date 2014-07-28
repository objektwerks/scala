name := "objektwerks.scala"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.0"
)

logLevel := Level.Info

scalacOptions ++= Seq("-unchecked", "-deprecation")
