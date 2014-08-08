package object implicits {
  def packMessage(message: String): String = {
    s"Message packed: $message"
  }
}