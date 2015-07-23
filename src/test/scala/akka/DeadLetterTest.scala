package akka

import java.util.concurrent.TimeUnit

import akka.actor._
import akka.util.Timeout
import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.concurrent.duration._

class Service extends Actor {
  def receive = {
    case message: String => println(s"Service: $message")
  }
}

class Listener extends Actor {
  def receive = {
    case deadLetter: DeadLetter => println(s"Deadletter: ${deadLetter.message}")
  }
}

class DeadLetterTest extends FunSuite  with BeforeAndAfterAll {
  private implicit val timeout = new Timeout(1, TimeUnit.SECONDS)
  private val system: ActorSystem = ActorSystem.create("funky")
  private val service: ActorRef = system.actorOf(Props[Service], name = "service")
  private val listener: ActorRef = system.actorOf(Props[Listener], name = "listener")
  system.eventStream.subscribe(listener, classOf[DeadLetter])

  override protected def afterAll(): Unit = {
    super.afterAll
    system.shutdown
    system.awaitTermination(3 seconds)
  }

  test("dead letter") {
    service ! "First message!"
    Thread.sleep(500)
    service ! PoisonPill
    Thread.sleep(500)
    service ! "Second message!"
  }
}