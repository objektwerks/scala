package akka

import akka.actor.{Actor, Props, Terminated}
import akka.event.Logging

class Actress(val name: String) extends Actor {
  val log = Logging(context.system, this)
  val starlet = context.actorOf(Props(new Starlet("Brightness")), name = "starlet")
  context.watch(starlet)
  log.info("A starlet was created: " + starlet)

  def receive = {
    case Message(0, message) => println(s"$name received " + message + " This is a local println message.")
    case Message(1, message) => sender ! s"$name received " + message + " This is a reply message."
    case Message(2, message) => starlet ! new Message(0, message)
    case Terminated(starlet) => println(s"$name received termination notice on: " + starlet.toString())
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