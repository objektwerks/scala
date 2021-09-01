import scala.annotation.tailrec

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

// Piping RLE.encode to RLE.decode with asserts.
import scala.util.chaining._
"aaaabbcccaeeeee"
  .pipe(RLE.encode)
  .tap(encoded => assert(encoded == "a4b2c3a1e5"))
  .pipe(RLE.decode)
  .tap(decoded => assert(decoded == "aaaabbcccaeeeee"))

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

/*
If you want separate lists of identical elements that appear consecutively, then
span will do that for you: it puts elements that pass the predicate (characters
that are the same as the first character) into its first returned list; but as
soon as the predicate fails for an element, that element and everything that follows
is put in the second list.
*/
val list = List(1, 1, 2, 2, 3, 3)
// 1st element is equal to 1, so all elements put in 2nd list
list.span(_ != 1)
// 3rd element is equal to 2, so all subsequent elements put in 2nd list
list.span(_ != 2)
// 5th element is equal to 3, so all subsequent elements put in 2nd list
list.span(_ != 3)

def group(chars: List[Char]): List[List[Char]] = {
  if (chars.isEmpty) List(List())
  else {
    val (firstChars, secondChars) = chars span { char => char == chars.head }
    if (secondChars == Nil) List(firstChars)
    else firstChars :: group(secondChars) // not tail-recursive
  }
}
val chars = "aabbbcccc".toCharArray().toList
val groups = group(chars)
groups map { chars => (chars.head, chars.length) }