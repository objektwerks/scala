package encoding

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.annotation.tailrec
import scala.util.Random

object RLE {
  case class Encoding(char: Char, count: Int) extends Product with Serializable

  // Only encodes letters.
  def encode(string: String): String = {
    def group(chars: List[Char]): List[List[Char]] = {
      if (chars.isEmpty) List(List())
      else {
        val (matchingChars, remainingChars) = chars span { char => char == chars.head }
        if (remainingChars == Nil) List(matchingChars)
        else matchingChars :: group(remainingChars) // not tail-recursive
      }
    }
    val letters = string.toCharArray.toList.filter(char => char.isLetter )
    letters match {
      case Nil => ""
      case _ =>
        val encodings = group(letters) map { chars => Encoding(chars.head, chars.length) }
        val encodedStrings = encodings map { encoding => encoding.char.toString + encoding.count.toString }
        encodedStrings.mkString
    }
  }

  // Only decodes letter-number pairs, multiplying letters by 1-2 digit numbers ( 1 - 99 ).
  def decode(string: String): String = {
    @tailrec
    def loop(chars: List[Char], acc: StringBuilder): String = {
      chars match {
        case Nil => acc.mkString
        case head :: tail =>
          if (head.isDigit) {
            if (tail.headOption.nonEmpty && tail.head.isDigit) {
              val times = head.asDigit.toString + tail.head.asDigit.toString
              loop(tail.tail, acc.append( acc.lastOption.getOrElse("").toString * ( times.toInt - 1 ) ) )
            } else loop(tail, acc.append( acc.lastOption.getOrElse("").toString * ( head.asDigit - 1 ) ) )
          } else loop(tail, acc.append(head))
      }
    }
    loop(string.toCharArray.toList, new StringBuilder())
  }
}

class RLETest extends AnyFunSuite with Matchers {
  test("encode") {
    println( s"*** RLE of aaaabbcccaeeeee : ${ RLE.encode("aaaabbcccaeeeee") }" )
    RLE.encode("aaaabbcccaeeeee") shouldBe "a4b2c3a1e5"

    println( s"*** RLE of empty string : ${ RLE.encode("") }" )
    RLE.encode("") shouldBe ""

    println( s"*** RLE of aaaaaaaaaaaaaaaaa : ${ RLE.encode("aaaaaaaaaaaaaaaaa") }" )
    RLE.encode("aaaaaaaaaaaaaaaaa") shouldBe "a17"

    println( s"*** RLE of 123 : ${ RLE.encode("123") }" )
    RLE.encode("123") shouldBe ""
  }

  test("decode") {
    println( s"*** RLD of a4b2c3a1e5 : ${ RLE.decode("a4b2c3a1e5") }" )
    RLE.decode("a4b2c3a1e5") shouldBe "aaaabbcccaeeeee"

    println( s"*** RLD of empty string : ${ RLE.decode("") }" )
    RLE.decode("") shouldBe ""

    println( s"*** RLD of a17 : ${ RLE.decode("a17") }" )
    RLE.decode("a17").length shouldBe 17

    println( s"*** RLD of 112131 : ${ RLE.decode("112131") }" )
    RLE.decode("112131") shouldBe ""
  }

  test("encode > decode letters") {
    for( i <- 1 to 5 ) {
      val random = Random.alphanumeric.filter(_.isLetter).map(_.toString * i).take(i).mkString
      val encoded = RLE.encode(random)
      val decoded = RLE.decode(encoded)
      println(s"$i random: $random - encoded: $encoded - decoded: $decoded")
      random shouldBe decoded
    }
  }

  test("encode > decode digits") {
    for( i <- 1 to 5 ) {
      val random = Random.alphanumeric.filter(_.isDigit).map(_.toString * i).take(i).mkString
      val encoded = RLE.encode(random)
      val decoded = RLE.decode(encoded)
      println(s"$i random: $random - encoded: $encoded - decoded: $decoded")
      encoded shouldBe ""
      decoded shouldBe ""
    }
  }

  test("pipe > tap > pipe") {
    import scala.util.chaining._
    "aaaabbcccaeeeee"
      .pipe(RLE.encode)
      .tap(e => e shouldBe "a4b2c3a1e5")
      .pipe(RLE.decode)
      .tap(e => e shouldBe "aaaabbcccaeeeee")
  }
}