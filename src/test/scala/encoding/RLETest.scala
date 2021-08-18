package encoding

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

final case class Encoding(char: Char, count: Int) extends Product with Serializable

object Encoding {
  def encode(value: String): String = {
    def group(chars: List[Char]): List[List[Char]] = {
      if (chars.isEmpty) List(List())
      else {
        val (grouped, next) = chars span { char => char == chars.head }
        if (next == Nil) List(grouped)
        else grouped :: group(next)
      }
    }
    val valueAsChars = value.toCharArray.toList
    val encodings = group(valueAsChars) map { chars => Encoding(chars.head, chars.length) }
    val encodedValues = encodings map { group =>
      group.char.toString + group.count.toString
    }
    encodedValues.mkString
  }

  def decode(value: String): String = {
    var decoded: String = ""
    var count: Int = 0
    val result = new StringBuilder()
    value.toCharArray.toList.foreach { char =>
      if ( char.isDigit ) {
        count = char.asDigit
        result.append( decoded * count )
      } else {
        decoded = char.toString
      }
    }
    result.mkString
  }
}

/**
 * Encoding for single letter occurences includes a 1 so that decode works correctly.
 * Decoding won't likely work for char counts beyond 9.
 */
class RLETest extends AnyFunSuite with Matchers {
  test("encode") {
    println( s" *** Run Length Encoding: ${ Encoding.encode("aaaabbcccaeeeee") }" )
    Encoding.encode("aaaabbcccaeeeee") shouldBe "a4b2c3a1e5"
  }

  test("decode") {
    println( s" *** Run Length Decoding: ${ Encoding.decode("a4b2c3a1e5") }" )
    Encoding.decode("a4b2c3a1e5") shouldBe "aaaabbcccaeeeee"
  }
}