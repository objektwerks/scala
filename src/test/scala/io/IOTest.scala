package io

import org.scalatest.{FunSuite, Matchers}

import scala.collection.MapView
import scala.io.{Codec, Source}
import scala.util.{Try, Using}

class IOTest extends FunSuite with Matchers {
  val quote = "You can avoid reality, but you cannot avoid the consequences of avoiding reality."

  test("from url") {
    val jokes = Source.fromURL("http://api.icndb.com/jokes/random/", Codec.UTF8.name).mkString.split("\\W+")
    jokes.nonEmpty shouldBe true
  }

  test("from file") {
    val words = Source.fromFile("./LICENSE", Codec.UTF8.name).mkString.split("\\W+")
    words.length shouldEqual 169
  }

  test("from input stream") {
    val words = Source.fromInputStream(getClass.getResourceAsStream("/license.mit"), Codec.UTF8.name).mkString.split("\\W+")
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
    val words = Source.fromBytes(quote.getBytes(Codec.UTF8.name), Codec.UTF8.name).mkString.split("\\W+")
    words.length shouldEqual 13
  }

  test("grouped") {
    val list = Source.fromInputStream(getClass.getResourceAsStream("/license.mit"), Codec.UTF8.name).mkString.split("\\W+").toList
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

  def fileToLines(file: String): Try[Seq[String]] = Using(Source.fromFile(file, Codec.UTF8.name)) { source => source.getLines.toSeq }
}