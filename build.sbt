name := "scala"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.16"
libraryDependencies ++= {
  Seq(
    "org.scala-lang.modules" %% "scala-async" % "1.0.1",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "2.4.0",
    "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4",
    "org.scala-lang" % "scala-reflect" % "2.13.14",
    "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
}
scalacOptions ++= Seq(
  "-Xasync"
)
