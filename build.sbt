name := "objektwerks.scala"

version := "1.0"

scalaVersion := "2.11.6"

resolvers ++= Seq(
  "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/",
  "Scalaz Bintray" at "http://dl.bintray.com/scalaz/releases"
)

libraryDependencies ++= {
  val akkaVersion = "2.3.9"
  val scalatraVersion = "2.4.0.RC1"
  val spayVersion = "1.3.3"
  val json4sVersion = "3.2.11"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.slick" % "slick_2.11" % "3.0.0-RC3",
    "com.typesafe" % "config" % "1.2.1",
    "org.scalaz" % "scalaz-core_2.11" % "7.1.1",
    "org.scala-lang.modules" % "scala-async_2.11" % "0.9.2",
    "org.scalafx" % "scalafx_2.11" % "8.0.40-R8",
    "net.databinder.dispatch" % "dispatch-core_2.11" % "0.11.2",
    "com.h2database" % "h2" % "1.4.187",
    "org.apache.spark" % "spark-core_2.11" % "1.3.0",
    "org.scalatra" % "scalatra_2.11" % scalatraVersion,
    "org.scalatra" %% "scalatra-json" % scalatraVersion,
    "org.json4s" % "json4s-jackson_2.11" % json4sVersion,
    "org.json4s" % "json4s-native_2.11" % json4sVersion,
    "org.eclipse.jetty" % "jetty-webapp" % "9.2.10.v20150310",
    "io.spray" %% "spray-can" % spayVersion,
    "io.spray" %% "spray-routing" % spayVersion,
    "io.spray" %% "spray-testkit" % spayVersion  % "test",
    "org.scalacheck" % "scalacheck_2.11" % "1.12.2" % "test",
    "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
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