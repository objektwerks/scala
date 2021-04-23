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

  test("pricing") {
    /* 
      Given the following csv data, select a single price for every host, store, and UPC combination.
      The price selected should be based on the day of the week that the price was collected, where the
      days are ranked by priority.

      Weekday priority (high to low): Wednesday(1), Thursday(2), Friday(3), Saturday(4), Tuesday(5), Monday(6), Sunday(7)

      Csv data is: 1 row corresponds to 1 line, all fields do not contain ','
      Csv schema is: date(0), host(1), store_id(2), postal_code(3), upc(4), price(5)
    */
    import scala.collection.mutable

    case class Pricing(date: String, host: String, store: String, upc: String, price: String)

    val lines = Using( Source.fromInputStream(getClass.getResourceAsStream("/pricing.csv"), utf8) ) { 
      source => source.getLines().toArray
    }.getOrElse( Array.empty[String] )

    val delimitter = ","
    val pricings = mutable.ArrayBuffer[Pricing]()
    for (line <- lines) {
      val columns = line.split(delimitter).map(_.trim)
      if ( columns.size == 6 ) {
        val date = columns(0)
        val host = columns(1)
        val store = columns(2)
        val upc = columns(4)
        val price = columns(5)

        val pricing = Pricing(date, host, store, upc, price)
        pricings += pricing
      }
    }
    val pricingsByDate = pricings.groupBy(_.date)
    for ( (key, value) <- pricingsByDate) {
      println()
      println(s"*** key: $key value: ${value.toString()}")
      println()
    }
  }

  def toWordCountMap(words: Array[String]): MapView[String, Int] =
    words.groupBy((word: String) => word.toLowerCase).view.mapValues(_.length)

  def fileToLines(file: String): Try[Seq[String]] = 
    Using( Source.fromFile(file, utf8) ) { 
      source => source.getLines().toSeq 
    }
}