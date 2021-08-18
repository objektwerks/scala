
object Encoding {
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
    val encodings = group(valueAsChars) map { chars => Encoding(chars.head, chars.length) }
    val encodedValues = encodings map { group =>
      group.char.toString + ( if (group.count == 1) "" else group.count.toString )
    }
    encodedValues.mkString
  }
}

// "aaaabbcccaeeeee" should encode to "a4b2c3ae5"
println( s" *** Run Length Encoding: ${ Encoding.encode("aaaabbcccaeeeee") }" )