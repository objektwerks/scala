package io

import org.scalatest.FunSuite

import scala.io.Source

class IOTest extends FunSuite {
  test("from file") {
    val words = Source.fromFile("./LICENSE").mkString.split("\\P{L}+")
    assert(words.size == 168)
  }

  test("from input stream") {
    val words = Source.fromInputStream(getClass.getResourceAsStream("/license.mit")).mkString.split("\\P{L}+")
    assert(words.size == 168)
  }
}