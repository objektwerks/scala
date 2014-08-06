package akka

import akka.actor.Actor

class Starlet(val name: String) extends Actor {
  def receive = {
    case Message(0, message) => println(s"$name received $message")
    case _ => println(Message(-1, s"$name received invalid message."))
  }
}