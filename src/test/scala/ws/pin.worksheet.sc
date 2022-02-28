import scala.util.Random

val specialChars = "~!@#$%^&*-+=<>?/:;".toList
private val random = new Random

def newSpecialChar: Char = specialChars(random.nextInt(specialChars.length))
def newPin: String =
  Random.shuffle(
    Random
      .alphanumeric
      .take(4)
      .mkString
      .prepended(newSpecialChar)
      .appended(newSpecialChar)
  ).mkString

val pin = newPin
pin.length