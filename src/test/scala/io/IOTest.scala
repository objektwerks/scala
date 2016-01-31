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

  test("from string") {
    val string = "You can avoid reality, but you cannot avoid the consequences of avoiding reality."
    val words = Source.fromString(string).mkString.split("\\P{L}+")
    assert(words.size == 13)
  }

  test("from chars") {
    val chars = "You can avoid reality, but you cannot avoid the consequences of avoiding reality.".toCharArray
    val words = Source.fromChars(chars).mkString.split("\\P{L}+")
    assert(words.size == 13)
  }

  test("from bytes") {
    val bytes = "You can avoid reality, but you cannot avoid the consequences of avoiding reality.".getBytes("UTF-8")
    val words = Source.fromBytes(bytes).mkString.split("\\P{L}+")
    assert(words.size == 13)
  }
}