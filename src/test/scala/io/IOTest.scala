package io

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.collection.MapView
import scala.io.{Codec, Source}
import scala.util.{Try, Using}

class IOTest extends AnyFunSuite with Matchers {
  val utf8 = Codec.UTF8.name
  val quote = "You can avoid reality, but you cannot avoid the consequences of avoiding reality."

  test("from url") {
    val jokes = Using( Source.fromURL("http://api.icndb.com/jokes/random/", utf8) ) { source => source.mkString.split("\\W+") }
    jokes.get.nonEmpty shouldBe true
  }

  test("from file") {
    val words = Using( Source.fromFile("./LICENSE", utf8) ) { source => source.mkString.split("\\W+") }
    words.get.length shouldEqual 169
  }

  test("from input stream") {
    val words = Source.fromInputStream(getClass.getResourceAsStream("/license.mit"), utf8).mkString.split("\\W+")
    words.length shouldEqual 169
    toWordCountMap(words).size shouldEqual 96
  }

  test("from string") {
    val words = Source.fromString(quote).mkString.split("\\W+")
    words.length shouldEqual 13
  }

  test("from chars") {
    val words = Source.fromChars(quote.toCharArray).mkString.split("\\W+")
    words.length shouldEqual 13
  }

  test("from bytes") {
    val words = Source.fromBytes(quote.getBytes(utf8), utf8).mkString.split("\\W+")
    words.length shouldEqual 13
  }

  test("grouped") {
    val list = Source.fromInputStream(getClass.getResourceAsStream("/license.mit"), utf8).mkString.split("\\W+").toList
    list.length shouldEqual 169

    val words = list.grouped(list.length / 8).toList
    words.length shouldEqual 9
  }

  test("file to lines") {
    fileToLines("build.sbt").isSuccess shouldBe true
    fileToLines("sbt.sbt").isFailure shouldBe true
  }

  def toWordCountMap(words: Array[String]): MapView[String, Int] = {
    words.groupBy((word: String) => word.toLowerCase).view.mapValues(_.length)
  }

  def fileToLines(file: String): Try[Seq[String]] = Using( Source.fromFile(file, utf8) ) { source => source.getLines.toSeq }
}