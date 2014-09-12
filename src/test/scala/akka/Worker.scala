package akka

import akka.actor.Actor

class Worker() extends Actor {
  println("Worker created.")

  def receive = {
    case Message(0, message) => println(s"Worker received $message")
    case _ => println(Message(-1, "Worker received invalid message."))
  }
}