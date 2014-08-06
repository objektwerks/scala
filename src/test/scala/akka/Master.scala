package akka

import akka.actor.{Actor, ActorRef, Props}

class Master(val name: String) extends Actor {
  val worker: ActorRef = context.actorOf(Props(new Worker("Worker")), name = "worker")
  println(s"\n$name created.")

  def receive = {
    case Message(0, message) => println(s"$name received $message")
    case Message(1, message) => sender ! s"$name received $message"
    case Message(2, message) => worker ! new Message(0, message)
    case _ => println(Message(-1, s"$name received invalid message."))
  }
}