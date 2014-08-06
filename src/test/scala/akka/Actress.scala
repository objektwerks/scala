package akka

import akka.actor.{Actor, Props}

class Actress(val name: String) extends Actor {
  val starlet = context.actorOf(Props(new Starlet("Starlet")), name = "starlet")
  println(s"\n$name created.")

  def receive = {
    case Message(0, message) => println(s"$name received $message")
    case Message(1, message) => sender ! s"$name received $message"
    case Message(2, message) => starlet ! new Message(0, message)
    case _ => println(Message(-1, s"$name received invalid message."))
  }
}