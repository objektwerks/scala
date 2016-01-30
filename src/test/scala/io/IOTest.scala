package io

import org.scalatest.FunSuite

import scala.io.Source

class IOTest extends FunSuite {
  test("from input stream") {
    val license = Source.fromInputStream(getClass.getResourceAsStream("/license.mit")).mkString
    val words = license.split("\\P{L}+")
    assert(words.size == 168)
  }
}