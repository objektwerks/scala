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

  test("from url") {
    val words = Source.fromURL("http://api.icndb.com/jokes/random/").mkString.split("\\P{L}+")
    assert(words.nonEmpty)
  }
}