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

sealed trait KindOf
case object Tell extends KindOf
case object TellWorker extends KindOf
case object Ask extends KindOf
case object AskWorker extends KindOf
case class Message(kindOf: KindOf, from: String, message: String)

class Master extends Actor {
  println(s"Master created: $self")
  private implicit val timeout = new Timeout(3, TimeUnit.SECONDS)
  private val worker: ActorRef = context.actorOf(Props[Worker], name = "worker")

  def receive = {
    case Message(Tell, from, message) => println(s"\nMaster received $message from $from.")
    case Message(TellWorker, from, message) => worker ! Message(Tell, s"$from -> Master", message)
    case Message(Ask, from, message) => sender ! s"Master received and responded to $message from $from."
    case Message(AskWorker, from, message) =>
      val future = worker ? Message(AskWorker, s"$from -> Master", message)
      future onComplete {
        case Success(returnMessage) => sender ! returnMessage
        case Failure(failure) => println(failure.getMessage); throw failure
      }
      Await.ready(future, Duration(3000, TimeUnit.SECONDS)) // Is there a better way?
    case _ => println("Master received an invalid message.")
  }

  override def preStart(): Unit = {
    super.preStart()
    println("Master pre-start event.")
  }

  override def postStop(): Unit = {
    super.postStop()
    println("Master post-stop event.")
  }
}

class Worker extends Actor {
  println(s"Worker created: $self")
  println(s"Worker parent: ${context.parent.path.name}")

  def receive = {
    case Message(Tell, from, message) => println(s"Worker received $message from $from.")
    case Message(AskWorker, from, message) => sender ! s"Worker received and responded to $message from $from."
    case _ => println("Worker received an invalid message.")
  }

  override def preStart(): Unit = {
    super.preStart()
    println("Worker pre-start event.")
  }

  override def postStop(): Unit = {
    super.postStop()
    println("Worker post-stop event.")
  }
}

class Identifier extends Actor {
  def receive = {
    case path: String => context.actorSelection(path) ! Identify(path)
    case ActorIdentity(path, Some(ref)) => println(s"Actor identified: $ref at path $path.")
    case ActorIdentity(path, None) => println(s"Actor NOT identified at path $path.")
  }
}

class ActorTellAskTest extends FunSuite with BeforeAndAfterAll {
  private implicit val timeout = new Timeout(1, TimeUnit.SECONDS)
  private val system: ActorSystem = ActorSystem.create("funky")
  private val master: ActorRef = system.actorOf(Props[Master], name = "master")
  private val identifier: ActorRef = system.actorOf(Props[Identifier], name = "identifier")
  println(s"Actor system created: $system")

  override protected def afterAll(): Unit = {
    super.afterAll
    println(s"Actor system shutdown: $system")
    system.shutdown
    system.awaitTermination
  }

  test("actor selection") {
    identifier ! "/user/*"
    identifier ! "/user/master/*"
    identifier ! "/funky/*"
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