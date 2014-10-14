package akka

import akka.actor.Actor

class Worker() extends Actor {
  println(s"Worker created: $self")

  def receive = {
    case Message(0, who, message) => println(s"Worker received $message from $who.")
    case _ => println("Worker received invalid message.")
  }
}