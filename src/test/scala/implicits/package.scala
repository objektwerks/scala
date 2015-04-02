package object implicits {
  def packMessage(message: String): String = {
    s"Message packed: $message"
  }

  val orderByTaskName = implicitly[Ordering[Task]](Ordering.by(t => t.name))
}