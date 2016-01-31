package io

import org.scalatest.FunSuite

import scala.io.Source

class IOTest extends FunSuite {
  val regex = "\\P{L}+"
  val quote = "You can avoid reality, but you cannot avoid the consequences of avoiding reality."

  test("from url") {
    val words = Source.fromURL("http://api.icndb.com/jokes/random/").mkString.split(regex)
    assert(words.nonEmpty)
  }

  test("from file") {
    val words = Source.fromFile("./LICENSE").mkString.split(regex)
    assert(words.size == 168)
  }

  test("from input stream") {
    val words = Source.fromInputStream(getClass.getResourceAsStream("/license.mit")).mkString.split(regex)
    assert(words.size == 168)
  }

  test("from string") {
    val words = Source.fromString(quote).mkString.split(regex)
    assert(words.size == 13)
  }

  test("from chars") {
    val words = Source.fromChars(quote.toCharArray).mkString.split(regex)
    assert(words.size == 13)
  }

  test("from bytes") {
    val words = Source.fromBytes(quote.getBytes("UTF-8")).mkString.split(regex)
    assert(words.size == 13)
  }
}