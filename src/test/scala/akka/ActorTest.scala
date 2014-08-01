package akka

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout
import org.scalatest.FunSuite

import scala.concurrent.{Await, ExecutionContext}
import scala.util.{Failure, Success}

class ActorTest extends FunSuite {
  test("async one way") {
    val system = ActorSystem.create("system")
    val actress = system.actorOf(Props(new Actress("Async One Way")), name = "actress")
    actress ! Message(0, "an async one way message!")
    actress ! Message(2, "a message for a starlet.")
    system.shutdown()
  }

  test("blocking two way") {
    implicit val timeout = new Timeout(3, TimeUnit.SECONDS)
    val system = ActorSystem.create("system")
    val actress = system.actorOf(Props(new Actress("Blocking Two Way")), name = "actress")
    val future = actress ? Message(1, "a blocking two way message!")
    val result = Await.result(future, timeout.duration).asInstanceOf[String]
    println(result)
    system.shutdown()
  }

  test("async two way") {
    implicit val ec = ExecutionContext.Implicits.global
    implicit val timeout = new Timeout(3, TimeUnit.SECONDS)
    val system = ActorSystem.create("system")
    val actress = system.actorOf(Props(new Actress("Async Two Way")), name = "actress")
    val future = actress ? Message(1, "an async two way message!")
    try {
      future onComplete {
        case Success(result) => println(result)
        case Failure(e) => println(e)
      }
    } finally {
      system.shutdown()
    }
  }

  test("poison pill") {
    val system = ActorSystem.create("system")
    val actress = system.actorOf(Props(new Actress("Async One Way")), name = "actress")
    actress ! Message(0, "an async one way message!")
    val starlet = system.actorSelection(actress.path./("starlet"))
    starlet ! PoisonPill
    Thread.sleep(3000)
    system.shutdown()
  }
}