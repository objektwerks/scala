package akka

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.{Await, ExecutionContext}
import scala.util.{Failure, Success}

class ActorTest extends FunSuite with BeforeAndAfter {
  implicit val ec = ExecutionContext.Implicits.global
  implicit val timeout = new Timeout(1, TimeUnit.SECONDS)
  var system: ActorSystem = _
  var master: ActorRef = _

  before {
    system = ActorSystem.create("system")
    master = system.actorOf(Props(new Master("Master")), name = "master")
  }

  after {
    system.shutdown()
  }

  test("async one way") {
    master ! Message(0, "an async one way message.")
    master ! Message(2, "an async one way message from master.")
  }

  test("blocking two way") {
    val future = master ? Message(1, "a blocking two way message.")
    val result = Await.result(future, timeout.duration).asInstanceOf[String]
    println(result)
  }

  test("async two way") {
    val future = master ? Message(1, "an async two way message.")
    try {
      future onComplete {
        case Success(result) => println(result)
        case Failure(e) => println(e)
      }
    } finally {
      master ! PoisonPill
      println("Master killed by poison pill.")
      Thread.sleep(1000)
    }
  }
}