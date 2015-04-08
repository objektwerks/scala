package object implicits {
  def packMessage(message: String): String = {
    s"Message packed: $message"
  }

  implicit def ord: Ordering[Task] = Ordering.by(t => t.name)
}