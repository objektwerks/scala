import scala.annotation.tailrec

object RLE {
  case class Encoding(char: Char, count: Int) extends Product with Serializable

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
    valueAsChars match {
      case Nil => ""
      case _ =>
        val encodings = group(valueAsChars) map { chars => Encoding(chars.head, chars.length) }
        val encodedValues = encodings map { group =>
          group.char.toString + group.count.toString
        }
        encodedValues.mkString
    }
  }

  def decode(value: String): String = {
    var decoded: String = ""
    var count: Int = 0
    val result = new StringBuilder()
    value.toCharArray.toList.foreach { char =>
      if ( char.isDigit ) {
        count = char.asDigit
        result.append( decoded * count )
      } else if ( char.isLetter ) {
        decoded = char.toString
      }
    }
    result.mkString
  }

  def decodex(value: String): String = {
    @tailrec
    def loop(chars: List[Char], acc: StringBuilder ): String = {
      chars match {
        case Nil => acc.mkString
        case head :: tail =>
          if (head.isDigit) {
            if (tail.head.isDigit) {
              val times = head.asDigit.toString + tail.head.asDigit.toString
              loop(tail.tail, acc.append( acc.last.toString * ( times.toInt - 1) ) )
            } else loop(tail, acc.append(head.asDigit))
          } else loop(tail, acc.append(head))
      }
    }
    loop(value.toCharArray.toList, new StringBuilder())
  }
}

// Encoding for single letter occurences includes a 1 so that decode works consistently.
// Decoding won't work for char counts beyond 9.

// "aaaabbcccaeeeee" should encode to "a4b2c3a1e5"
println( s"*** Run Length Encoding: ${ RLE.encode("aaaabbcccaeeeee") }" )
println( s"*** Run Length Encoding: ${ RLE.encode("") }" )

// "a4b2c3a1e5" should decode to "aaaabbcccaeeeee"
println( s"*** Run Length Decoding: ${ RLE.decode("a4b2c3a1e5") }" )
println( s"*** Run Length Decoding: ${ RLE.decode("") }" )
println( s"*** Run Length Decoding: ${ RLE.decodex("a17") }" )
RLE.decodex("a17").length == 17