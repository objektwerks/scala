package akka

import java.util.concurrent.TimeUnit

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.{global => ec}
import scala.concurrent.duration.Duration
import scala.util.Success

sealed trait Mode
case object Tell extends Mode
case object TellWorker extends Mode
case object Ask extends Mode
case object AskWorker extends Mode
case class Message(mode: Mode, who: String, message: String)

class Master extends Actor {
  println(s"Master created: $self")
  private implicit val timeout = new Timeout(1, TimeUnit.SECONDS)
  val worker: ActorRef = context.actorOf(Props(new Worker()), name = "worker")

  def receive = {
    case Message(Tell, who, message) => println(s"\nMaster received $message from $who.")
    case Message(TellWorker, who, message) => worker ! Message(Tell, s"$who -> Master", message)
    case Message(Ask, who, message) => sender ! s"Master received $message from $who."
    case Message(AskWorker, who, message) => val future = worker ? Message(AskWorker, s"$who -> Master", message)
      future onSuccess {
        case Success(returnMessage) => sender ! returnMessage
      }
      Await.ready(future, Duration(1000, TimeUnit.SECONDS))
    case _ => println("Master received invalid message.")
  }
}

class Worker extends Actor {
  println(s"Worker created: $self")
  def receive = {
    case Message(Tell, who, message) => println(s"Worker received $message from $who.")
    case Message(AskWorker, who, message) => sender ! s"Worker received $message from $who."
    case _ => println("Worker received invalid message.")
  }
}

class ActorTest extends FunSuite with BeforeAndAfterAll {
  private implicit val timeout = new Timeout(1, TimeUnit.SECONDS)
  private val system: ActorSystem = ActorSystem.create("system")
  private val master: ActorRef = system.actorOf(Props(new Master()), name = "master")
  println(s"Actor system created: $system")

  override protected def afterAll(): Unit = {
    super.afterAll()
    println(s"Actor system shutdown: $system")
    system.shutdown()
    system.awaitTermination()
  }

  test("async tell ! -> system tell master") {
    master ! Message(Tell, "System", "an async one way ! -> tell message")
  }

  test("async tell ! -> system tell worker via master") {
    master ! Message(TellWorker, "System", "an async one way ! -> tell worker message")
  }

  test("async ask ? -> system ask master") {
    val future = master ? Message(Ask, "System", "an async two way ? -> ask message")
    future onSuccess {
      case Success(message) => println(message)
    }
  }

  test("async ask ? -> system ask worker via master") {
    val future = master ? Message(AskWorker, "System", "an async two way ? -> ask worker message")
    future onSuccess  {
      case Success(message) => println(message)
    }
  }
}