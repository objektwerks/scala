import scala.util.Random

Random.alphanumeric.take(9).mkString
Random.alphanumeric.take(9).mkString
Random.alphanumeric.take(9).mkString

Random.shuffle( Random.alphanumeric.take(7).mkString.prepended("@").appended("!") ).mkString
Random.shuffle( Random.alphanumeric.take(7).mkString.prepended("@").appended("!") ).mkString
Random.shuffle( Random.alphanumeric.take(7).mkString.prepended("@").appended("!") ).mkString

val specialChars = "~!@#$%^&*()-+<>?".toList
val random = new Random

def newPin: String = Random.shuffle(
  Random
    .alphanumeric
    .take(7)
    .mkString
    .prepended(newSpecialChar)
    .appended(newSpecialChar)
).mkString

def newSpecialChar: Char = {
  val list = random.shuffle(specialChars)
  list(random.nextInt(list.length))
}

newPin
newPin
newPin