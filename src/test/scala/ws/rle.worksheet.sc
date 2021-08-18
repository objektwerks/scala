sealed trait ELR extends Product with Serializable
final case object Empty extends ELR
final case class Encoding(character: Char, count: Int) extends ELR

def encode(value: String): String = value match {
  case value if value.isEmpty => ""
  case _ =>
    val chars = value.toCharArray.map(char => Encoding(char, 1))
    val iterator = chars.iterator
    val encodings = List.empty[Encoding]
    do {
      val current = iterator.next()
      val encoding = encodings.find( encoding => encoding.character == current.character )
      if ( encoding.nonEmpty ) encoding.get.copy(current.character, current.count + 1)
      else encodings :+ Encoding( current.character, current.count )
    } while ( iterator.nonEmpty )
    val result = new StringBuilder()
    encodings.foreach { encoding => 
      result.append( encoding.character + encoding.count )
    }
    result.toString
}

// RLE: aaaabbcccaeeeee => a4b2c3ae5
encode("aaaabbcccaeeeee")