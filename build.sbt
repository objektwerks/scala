name := "objektwerks.scala"

version := "1.0"

scalaVersion := "2.11.6"
ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

resolvers ++= Seq(
  "Scalaz Bintray" at "http://dl.bintray.com/scalaz/releases"
)

libraryDependencies ++= {
  val akkaVersion = "2.3.11"
  val sprayVersion = "1.3.3"
  val json4sVersion = "3.2.11"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.slick" % "slick_2.11" % "3.0.0",
    "com.typesafe" % "config" % "1.2.1",
    "org.scalaz" % "scalaz-core_2.11" % "7.1.2",
    "org.scala-lang.modules" % "scala-async_2.11" % "0.9.2",
    "org.scalafx" % "scalafx_2.11" % "8.0.40-R8",
    "net.databinder.dispatch" % "dispatch-core_2.11" % "0.11.2",
    "com.h2database" % "h2" % "1.4.187",
    "org.apache.spark" % "spark-core_2.11" % "1.3.1",
    "org.apache.spark" % "spark-streaming_2.11" % "1.3.1",
    "org.json4s" % "json4s-jackson_2.11" % json4sVersion,
    "org.json4s" % "json4s-native_2.11" % json4sVersion,
    "io.spray" %% "spray-can" % sprayVersion,
    "io.spray" %% "spray-routing" % sprayVersion,
    "io.spray" %%  "spray-json" % "1.3.1",
    "org.scala-lang" % "scala-pickling_2.11" % "0.9.1",
    "org.sorm-framework" % "sorm" % "0.3.18",
    "org.slf4j" % "slf4j-api" % "1.7.12",
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "io.spray" %% "spray-testkit" % sprayVersion  % "test",
    "org.scalacheck" % "scalacheck_2.11" % "1.12.2" % "test",
    "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
    "org.specs2" %% "specs2-core" % "2.4.17" % "test"
  )
}

scalacOptions ++= Seq(
  "-language:postfixOps",
  "-language:implicitConversions",
  "-language:reflectiveCalls",
  "-language:higherKinds",
  "-feature",
  "-deprecation",
  "-Xlint",
  "-Xfatal-warnings" // 2.12 flags (blows up ide compiler. "-Xexperimental", "-Ydelambdafy:method"
)

fork in test := true

javaOptions += "-server -Xss1m -Xmx2g"

logLevel := Level.Info
