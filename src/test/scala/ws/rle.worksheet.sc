import scala.annotation.tailrec

object RLE {
  case class Encoding(char: Char, count: Int) extends Product with Serializable

  // Only encodes letters.
  def encode(value: String): String = {
    def group(chars: List[Char]): List[List[Char]] = {
      if (chars.isEmpty) List(List())
      else {
        val (grouped, next) = chars span { char => char == chars.head }
        if (next == Nil) List(grouped)
        else grouped :: group(next)
      }
    }
    val letters = value.toCharArray.toList.filter( char => char.isLetter )
    letters match {
      case Nil => ""
      case _ =>
        val encodings = group(letters) map { chars => Encoding(chars.head, chars.length) }
        val encodedValues = encodings map { group =>
          group.char.toString + group.count.toString
        }
        encodedValues.mkString
    }
  }

  // Only decodes letter-number pairs, expanding letters up to 2 digit places ( 1 - 99 ).
  def decode(value: String): String = {
    @tailrec
    def loop(chars: List[Char], acc: StringBuilder ): String = {
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
    loop(value.toCharArray.toList, new StringBuilder())
  }
}

// "aaaabbcccaeeeee" should encode to "a4b2c3a1e5"
println( s"*** RLE of aaaabbcccaeeeee : ${ RLE.encode("aaaabbcccaeeeee") }" )
println( s"*** RLE of empty : ${ RLE.encode("") }" )

// "a4b2c3a1e5" should decode to "aaaabbcccaeeeee"
println( s"*** RLD of a4b2c3a1e5 : ${ RLE.decode("a4b2c3a1e5") }" )
println( s"*** RLD of empty : ${ RLE.decode("") }" )

// "a17" should decode to "a" * 17
println( s"*** RLD of a17 : ${ RLE.decode("a17") }" )
RLE.decode("a17").length == 17

// 123 should encode to "". 112131 should decode to ""
println( s"*** RLE of 123 : ${ RLE.encode("123") }" )
println( s"*** RLD of 112131 : ${ RLE.decode("112131") }" )