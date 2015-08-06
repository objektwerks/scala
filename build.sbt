name := "objektwerks.scala"

version := "1.0"

scalaVersion := "2.11.7"
ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

resolvers ++= Seq(
  "Scalaz Bintray" at "http://dl.bintray.com/scalaz/releases"
)

libraryDependencies ++= {
  val akkaVersion = "2.3.12"
  val sprayVersion = "1.3.3"
  val json4sVersion = "3.2.11"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.slick" % "slick_2.11" % "3.0.0",
    "com.typesafe" % "config" % "1.3.0",
    "org.scalaz" % "scalaz-core_2.11" % "7.1.2",
    "org.scala-lang.modules" % "scala-async_2.11" % "0.9.4",
    "org.scalafx" % "scalafx_2.11" % "8.0.40-R8",
    "net.databinder.dispatch" % "dispatch-core_2.11" % "0.11.3",
    "com.h2database" % "h2" % "1.4.187",
    "org.json4s" % "json4s-jackson_2.11" % json4sVersion,
    "org.json4s" % "json4s-native_2.11" % json4sVersion,
    "io.spray" %% "spray-can" % sprayVersion,
    "io.spray" %% "spray-routing" % sprayVersion,
    "io.spray" %%  "spray-json" % "1.3.2",
    "org.sorm-framework" % "sorm" % "0.3.18",
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "io.spray" %% "spray-testkit" % sprayVersion  % "test",
    "org.scalacheck" % "scalacheck_2.11" % "1.12.4" % "test",
    "org.scalatest" % "scalatest_2.11" % "2.2.5" % "test",
    "org.specs2" %% "specs2-core" % "2.4.17" % "test"
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
