name := "scala"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.5"
libraryDependencies ++= {
  Seq(
    "org.scala-lang.modules" %% "scala-async" % "0.10.0",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
    "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.0",
    "org.scala-lang" % "scala-reflect" % "2.13.4",
    "org.scalatest" %% "scalatest" % "3.2.6" % Test
  )
}