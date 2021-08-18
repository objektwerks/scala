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
      group.char.toString + ( if (group.count == 1) "" else group.count.toString )
    }
    encodedValues.mkString
  }
}

class RLETest extends AnyFunSuite with Matchers {
  import Encoding._
  test("encode") {
    println( s" *** Run Length Encoding: ${ encode("aaaabbcccaeeeee") }" )
    encode("aaaabbcccaeeeee") shouldBe "a4b2c3ae5"
  }
}