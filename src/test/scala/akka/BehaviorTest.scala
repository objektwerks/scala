package akka

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.util.Timeout
import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.concurrent.duration._

case object Ready
case object Swim
case object Bike
case object Run
case object Finish

class Triathlete extends Actor {
  def receive = prepare

  def prepare: Actor.Receive = {
    case Ready => println("Triathlete ready!")
    case Swim => println("Triathlete swimming!"); context.become(swim)
  }

  def swim: Actor.Receive = {
    case Bike => println("Triathlete biking!"); context.become(bike)
  }

  def bike: Actor.Receive = {
    case Run => println("Triathlete running!"); context.become(run)
  }

  def run: Actor.Receive = {
    case Finish => println("Triathlete finished race!"); context.become(prepare)
  }

  override def unhandled(message: Any): Unit = {
    super.unhandled(message)
    println(s"Triathlete failed to handle message: $message.")
  }
}

class BehaviorTest extends FunSuite with BeforeAndAfterAll {
  private implicit val timeout = new Timeout(1, TimeUnit.SECONDS)
  private val system: ActorSystem = ActorSystem.create("funky")
  private val triathlete: ActorRef = system.actorOf(Props[Triathlete], name = "triathlete")

  override protected def afterAll(): Unit = {
    super.afterAll
    system.shutdown
    system.awaitTermination(3 seconds)
  }

  test("race") {
    race
    race
  }

  private def race(): Unit = {
    triathlete ! Ready
    triathlete ! Swim
    triathlete ! Bike
    triathlete ! Run
    triathlete ! Finish
  }
}