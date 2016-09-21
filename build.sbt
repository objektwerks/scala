name := "scala"
organization := "objektwerks"
version := "1.0"
scalaVersion := "2.12.0-RC1"
ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
libraryDependencies ++= {
  Seq(
    "org.scalatest" % "scalatest_2.12.0-RC1" % "3.0.0" % "test"
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