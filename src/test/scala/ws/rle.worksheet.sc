// RLE: aaaabbcccaeeeee => a4b2c3ae5

import scala.annotation.tailrec

val string = "aaaabbcccaeeeee"
val chars = string.toCharArray
val grouped = chars
  .groupBy( char => char )
  .view
  .mapValues(_.length)

val tuples = chars.map { char => (char, 1) }

def encode(string: String): String = {
  @tailrec
  def loop(chars: List[(Char, Int)], acc: StringBuilder): String = chars match {
    case Nil => acc.toString
    case head :: tail => loop(tail, acc += head._1)
  }
  loop(string.toList.map { char => (char, 1) }, new StringBuilder())
}

encode(string)