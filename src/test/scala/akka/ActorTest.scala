package akka

import java.util.concurrent.TimeUnit

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import org.scalatest.FunSuite

import scala.concurrent.ExecutionContext.Implicits.{global => ec}
import scala.util.{Failure, Success}

class Master extends Actor {
  println(s"Master created: $self")
  val worker: ActorRef = context.actorOf(Props(new Worker()), name = "worker")

  def receive = {
    case Message(Tell, who, message) => println(s"\nMaster received $message from $who.")
    case Message(Ask, who, message) => sender ! s"Master received $message from $who."
    case Message(TellDelegate, who, message) => worker ! new Message(Tell, "Master", message)
    case _ => println("Master received invalid message.")
  }
}

class Worker extends Actor {
  println(s"Worker created: $self")
  def receive = {
    case Message(Tell, who, message) => println(s"Worker received $message from $who.")
    case _ => println("Worker received invalid message.")
  }
}

sealed trait Mode
case object Tell extends Mode
case object TellDelegate extends Mode
case object Ask extends Mode
case class Message(mode: Mode, who: String, message: String)

class ActorTest extends FunSuite {
  private implicit val timeout = new Timeout(1, TimeUnit.SECONDS)
  private val system: ActorSystem = ActorSystem.create("system")
  private val master: ActorRef = system.actorOf(Props(new Master()), name = "master")
  println(s"Actor system created: $system")

  test("async one way ! -> tell") {
    master ! Message(Tell, "System", "an async one way ! -> tell message")
    master ! Message(TellDelegate, "System", "an async one way ! -> tell message")
  }

  test("async two way ? -> ask") {
    val future = master ? Message(Ask, "System", "an async two way ? -> ask message")
    future onComplete {
      case Success(result) => kill(result)
      case Failure(e) => kill(e)
    }
  }

  private def kill(result: Any) {
    println(result)
    println(s"Actor system shutdown: $system")
    system.shutdown()
  }
}