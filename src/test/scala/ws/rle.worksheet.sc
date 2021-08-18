
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

// Encoding for single letter occurences includes a 1 so that decode works correctly.
// Decoding won't likely work for char counts beyond 9.

// "aaaabbcccaeeeee" should encode to "a4b2c3a1e5"
println( s" *** Run Length Encoding: ${ Encoding.encode("aaaabbcccaeeeee") }" )

// "a4b2c3a1e5" should decode to "aaaabbcccaeeeee"
println( s" *** Run Length Decoding: ${ Encoding.decode("a4b2c3a1e5") }" )