name := "objektwerks.scala"
version := "1.0"
scalaVersion := "2.11.7"
ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
libraryDependencies ++= {
  Seq(
    "com.typesafe" % "config" % "1.3.0",
    "org.scala-lang.modules" % "scala-async_2.11" % "0.9.4",
    "org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.4",
    "ch.qos.logback" % "logback-classic" % "1.1.3",
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