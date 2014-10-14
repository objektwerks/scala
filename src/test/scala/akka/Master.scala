package akka

import akka.actor.{Actor, ActorRef, Props}

class Master() extends Actor {
  println(s"Master created: $self")
  val worker: ActorRef = context.actorOf(Props(new Worker()), name = "worker")

  def receive = {
    case Message(Tell, who, message) => println(s"\nMaster received $message from $who.")
    case Message(Ask, who, message) => sender ! s"Master received $message from $who."
    case Message(TellDelegate, who, message) => worker ! new Message(Tell, "Master", message)
    case _ => println("Master received invalid message.")
  }
}