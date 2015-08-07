name := "objektwerks.scala"

version := "1.0"

scalaVersion := "2.11.7"
ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

resolvers ++= Seq(
  "Scalaz Bintray" at "http://dl.bintray.com/scalaz/releases"
)

libraryDependencies ++= {emov
  val json4sVersion = "3.2.11"
  Seq(
    "com.typesafe" % "config" % "1.3.0",
    "org.scalaz" % "scalaz-core_2.11" % "7.1.2",
    "org.scala-lang.modules" % "scala-async_2.11" % "0.9.4",
    "org.scalafx" % "scalafx_2.11" % "8.0.40-R8",
    "net.databinder.dispatch" % "dispatch-core_2.11" % "0.11.3",
    "org.json4s" % "json4s-jackson_2.11" % json4sVersion,
    "org.json4s" % "json4s-native_2.11" % json4sVersion,
    "org.slf4j" % "slf4j-api" % "1.7.12",
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "org.scalacheck" % "scalacheck_2.11" % "1.12.4" % "test",
    "org.scalatest" % "scalatest_2.11" % "2.2.5" % "test"
  )
}

scalacOptions ++= Seq(
  "-language:postfixOps",
  "-language:implicitConversions",
  "-language:reflectiveCalls",
  "-language:higherKinds",
  "-feature",
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-Xfatal-warnings"
)

fork in test := true

javaOptions += "-server -Xss1m -Xmx2g"

logLevel := Level.Info
