package akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.duration._

class Ping extends Actor {
  def receive = {
    case ping: String => println(ping); sender ! ping
    case _ => println("TestActor received invalid message.")
  }
}

class ActorTestKit extends TestKit(ActorSystem("funky")) with ImplicitSender
with WordSpecLike with Matchers with BeforeAndAfterAll {
  private val ping: ActorRef = system.actorOf(Props[Ping], name = "ping")

  override protected def afterAll(): Unit = {
    super.afterAll
    println(s"Actor system shutdown: $system")
    system.shutdown
    system.awaitTermination
  }

  "Ping Actor" should {
    "reply with an identical message" in {
      within(2 seconds) {
        ping ! "ping"
        expectMsg("ping")
      }
    }
  }
}