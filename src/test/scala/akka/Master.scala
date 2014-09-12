package akka

import akka.actor.{Actor, ActorRef, Props}

class Master() extends Actor {
  val worker: ActorRef = context.actorOf(Props(new Worker()), name = "worker")
  println("Master created.")

  def receive = {
    case Message(0, message) => println(s"\nMaster received $message")
    case Message(1, message) => sender ! s"Master received $message"
    case Message(2, message) => worker ! new Message(0, message)
    case _ => println(Message(-1, "Master received invalid message."))
  }
}