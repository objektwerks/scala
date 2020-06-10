name := "scala"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.2"
libraryDependencies ++= {
  Seq(
    "org.scala-lang.modules" %% "scala-async" % "0.10.0" % Test,
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2" % Test,
    "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0" % Test,
    "org.scalatest" %% "scalatest" % "3.1.2" % Test
  )
}
scalacOptions ++= Seq(
  "-language:postfixOps",
  "-language:reflectiveCalls",
  "-language:implicitConversions",
  "-language:higherKinds"
)