package io

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.collection.MapView
import scala.io.{Codec, Source}
import scala.util.{Try, Using}

class IOTest extends AnyFunSuite with Matchers {
  val utf8 = Codec.UTF8.name
  val quote = "You can avoid reality, but you cannot avoid the consequences of avoiding reality."

  def toWordCountMap(words: Array[String]): MapView[String, Int] =
    words
      .groupBy((word: String) => word.toLowerCase)
      .view
      .mapValues(_.length)

  def fileToLines(file: String): Try[Seq[String]] = 
    Using( Source.fromFile(file, utf8) ) { 
      source => source.getLines().toSeq 
    }

  test("from url") {
    val jokes = Using( Source.fromURL("http://api.icndb.com/jokes/random/", utf8) ) { 
      source => source.mkString.split("\\W+") 
    }.getOrElse( Array.empty[String] )
    jokes.nonEmpty shouldBe true
  }

  test("from file") {
    val words = Using( Source.fromFile("./LICENSE", utf8) ) { 
      source => source.mkString.split("\\W+") 
    }.getOrElse( Array.empty[String] )
    words.length shouldEqual 1427
  }

  test("from input stream") {
    val words = Using( Source.fromInputStream(getClass.getResourceAsStream("/license.mit"), utf8) ) { 
      source => source.mkString.split("\\W+")
    }.getOrElse( Array.empty[String] )
    words.length shouldEqual 169
    toWordCountMap(words).size shouldEqual 96
  }

  test("from string") {
    val words = Using( Source.fromString(quote) ) { 
      source => source.mkString.split("\\W+") 
    }.getOrElse( Array.empty[String] )
    words.length shouldEqual 13
  }

  test("from chars") {
    val words = Using( Source.fromChars(quote.toCharArray) ) {
      source => source.mkString.split("\\W+") 
    }.getOrElse( Array.empty[String] )
    words.length shouldEqual 13
  }

  test("from bytes") {
    val words = Using( Source.fromBytes(quote.getBytes(utf8), utf8) ) {
      source => source.mkString.split("\\W+") 
    }.getOrElse( Array.empty[String] )
    words.length shouldEqual 13
  }

  test("grouped") {
    val array = Using( Source.fromInputStream(getClass.getResourceAsStream("/license.mit"), utf8) ) {
      source => source.mkString.split("\\W+") 
    }.getOrElse( Array.empty[String] )
    array.length shouldEqual 169

    val words = array.grouped(array.length / 8).toList
    words.length shouldEqual 9
  }

  test("file to lines") {
    fileToLines("build.sbt").isSuccess shouldBe true
    fileToLines("sbt.sbt").isFailure shouldBe true
  }
}