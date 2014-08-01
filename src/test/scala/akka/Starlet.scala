package akka

import akka.actor.Actor

class Starlet(val name: String) extends Actor {
  def receive = {
    case Message(0, message) => println(s"$name received " + message + " This is a local println message.")
    case _ => println(Message(-1, "Invalid message received!"))
  }

  override def preStart() {
    println(s"$name : preStart")
    super.preStart()
  }

  override def postStop() {
    println(s"$name : postStop")
    super.postStop()
  }

  override def preRestart(reason: Throwable, message: Option[Any]) {
    println(s"$name : preRestart")
    println("reason: " + reason.getMessage)
    println("message: " + message.toString)
    super.preRestart(reason, message)
  }

  override def postRestart(reason: Throwable) {
    println(s"$name : postRestart")
    println("reason: " + reason.getMessage)
    super.postRestart(reason)
  }
}