package io

import org.scalatest.FunSuite

import scala.io.{Codec, Source}

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
    assert(toWordCount(words).size == 115)
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
    val words = Source.fromBytes(quote.getBytes(Codec.UTF8.name)).mkString.split(regex)
    assert(words.size == 13)
  }

  def toWordCount(words: Array[String]): Map[String, Int] = {
    words.groupBy((word:String) => word).mapValues(_.length)
  }
}