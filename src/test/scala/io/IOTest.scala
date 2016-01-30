package io

import org.scalatest.FunSuite

import scala.io.Source

class IOTest extends FunSuite {
  test("io") {
    val license = Source.fromInputStream(getClass.getResourceAsStream("/license.mit")).mkString
    println(license)
  }
}