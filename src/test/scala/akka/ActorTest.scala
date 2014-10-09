package akka

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout
import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class ActorTest extends FunSuite {
  private implicit val timeout = new Timeout(1, TimeUnit.SECONDS)
  private val system: ActorSystem = ActorSystem.create("system")
  private val master: ActorRef = system.actorOf(Props(new Master()), name = "master")
  println("Actor system started.")

  test("async one way tell") {
    master ! Message(0, "an async one way message.")
    master ! Message(2, "an async one way message from master.")
  }

  test("blocking two way ask") {
    val future = master ? Message(1, "a blocking two way message.")
    val result = Await.result(future, timeout.duration).asInstanceOf[String]
    println(result)
  }

  test("async two way ask") {
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
      system.shutdown()
      println("Actor system shutdown.")
    }
  }
}