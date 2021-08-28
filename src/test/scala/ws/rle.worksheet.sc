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
    @tailrec
    def loop(chars: List[Char], acc: StringBuilder ): String = {
      chars match {
        case Nil => acc.mkString
        case head :: tail =>
          if (head.isDigit) {
            if (tail.headOption.nonEmpty && tail.head.isDigit) {
              val times = head.asDigit.toString + tail.head.asDigit.toString
              loop(tail.tail, acc.append( acc.last.toString * ( times.toInt - 1) ) )
            } else loop(tail, acc.append( acc.last.toString * ( head.asDigit - 1 ) ) )
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