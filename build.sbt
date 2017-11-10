name := "scala"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.12.4"
libraryDependencies ++= {
  Seq(
    "org.scalatest" % "scalatest_2.12" % "3.0.4" % "test",
    "org.scala-lang.modules" % "scala-async_2.12" % "0.9.7" % "test",
    "org.scala-lang.modules" % "scala-parser-combinators_2.12" % "1.0.6" % "test"
  )
}
scalacOptions ++= Seq(
  "-language:postfixOps",
  "-language:reflectiveCalls",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-feature",
  "-Ywarn-unused-import",
  "-Ywarn-unused",
  "-Ywarn-dead-code",
  "-unchecked",
  "-deprecation",
  "-Xfatal-warnings",
  "-Xlint:missing-interpolator",
  "-Xlint"
)
fork in test := true
javaOptions += "-server -Xss1m -Xmx2g"