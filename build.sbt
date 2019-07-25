name := "scala"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.12.8"
libraryDependencies ++= {
  Seq(
    "org.scalatest" %% "scalatest" % "3.0.8" % Test,
    "org.scala-lang.modules" %% "scala-async" % "0.10.0" % Test,
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2" % Test
  )
}
scalacOptions ++= Seq(
  "-language:postfixOps",
  "-language:reflectiveCalls",
  "-language:implicitConversions",
  "-language:higherKinds"
)