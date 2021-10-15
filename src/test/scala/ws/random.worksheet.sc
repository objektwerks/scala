import scala.util.Random

Random.alphanumeric.take(9).mkString
Random.alphanumeric.take(9).mkString
Random.alphanumeric.take(9).mkString

val specialChars = "~!@#$%^&*{}-+<>?/:;".toList
val random = new Random

def newPin: String = Random.shuffle(
  Random
    .alphanumeric
    .take(7)
    .mkString
    .prepended(newSpecialChar)
    .appended(newSpecialChar)
  ).mkString

def newSpecialChar: Char = specialChars(random.nextInt(specialChars.length))

newPin
newPin
newPin