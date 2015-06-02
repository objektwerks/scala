package akka

import java.util.concurrent.TimeUnit

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.{global => ec}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

sealed trait Category
case object Tell extends Category
case object TellWorker extends Category
case object Ask extends Category
case object AskWorker extends Category
case class Message(category: Category, who: String, message: String)

class Master extends Actor {
  println(s"Master created: $self")
  private implicit val timeout = new Timeout(3, TimeUnit.SECONDS)
  val worker: ActorRef = context.actorOf(Props(new Worker), name = "worker")

  def receive = {
    case Message(Tell, who, message) => println(s"\nMaster received $message from $who.")
    case Message(TellWorker, who, message) => worker ! Message(Tell, s"$who -> Master", message)
    case Message(Ask, who, message) => sender ! s"Master received and responded to $message from $who."
    case Message(AskWorker, who, message) =>
      val future = worker ? Message(AskWorker, s"$who -> Master", message)
      future onComplete {
        case Success(returnMessage) => sender ! returnMessage
        case Failure(failure) => println(failure.getMessage); throw failure
      }
      Await.ready(future, Duration(3000, TimeUnit.SECONDS)) // Is there a better way?
    case _ => println("Master received invalid message.")
  }
}

class Worker extends Actor {
  println(s"Worker created: $self")
  def receive = {
    case Message(Tell, who, message) => println(s"Worker received $message from $who.")
    case Message(AskWorker, who, message) => sender ! s"Worker received and responded to $message from $who."
    case _ => println("Worker received invalid message.")
  }
}

class ActorTest extends FunSuite with BeforeAndAfterAll {
  private implicit val timeout = new Timeout(1, TimeUnit.SECONDS)
  private val system: ActorSystem = ActorSystem.create("system")
  private val master: ActorRef = system.actorOf(Props(new Master), name = "master")
  println(s"Actor system created: $system")

  override protected def afterAll(): Unit = {
    super.afterAll
    println(s"Actor system shutdown: $system")
    system.shutdown
    system.awaitTermination
  }

  test("system ! master") {
    master ! Message(Tell, "System", "tell ! message")
  }

  test("system ! master ! worker") {
    master ! Message(TellWorker, "System", "tell ! message")
  }

  test("system ? master") {
    val future = master ? Message(Ask, "System", "ask ? message")
    future onComplete {
      case Success(message) => println(message)
      case Failure(failure) => println(failure.getMessage); throw failure
    }
  }

  test("system ? master ? worker") {
    val future = master ? Message(AskWorker, "System", "ask ? message")
    future onComplete  {
      case Success(message) => println(message)
      case Failure(failure) => println(failure.getMessage); throw failure
    }
  }
}