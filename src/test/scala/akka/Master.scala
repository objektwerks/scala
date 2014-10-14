package akka

import akka.actor.{Actor, ActorRef, Props}

class Master() extends Actor {
  println(s"Master created: $self")
  val worker: ActorRef = context.actorOf(Props(new Worker()), name = "worker")

  def receive = {
    case Message(0, who, message) => println(s"\nMaster received $message from $who.")
    case Message(1, who, message) => sender ! s"Master received $message from $who."
    case Message(2, who, message) => worker ! new Message(0, "Master", message)
    case _ => println("Master received invalid message.")
  }
}