package akka

import java.util.concurrent.TimeUnit

import akka.actor.SupervisorStrategy.{Restart, Stop}
import akka.actor._
import akka.util.Timeout
import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.concurrent.ExecutionContext.Implicits.{global => ec}
import scala.concurrent.duration._

trait Task
case object Play extends Task
case object CleanRoom extends Task
case object CleanWindows extends Task
case object CleanBathroom extends Task
case object StandInCorner extends Task

class CleanRoomException(cause: String) extends Exception(cause)
class CleanWindowsException(cause: String) extends Exception(cause)
class CleanBathroomException(cause: String) extends Exception(cause)
class StandInCornerException(cause: String) extends Exception(cause)

class Nanny extends Actor {
  println(s"Nanny created: $self")
  private implicit val timeout = new Timeout(3, TimeUnit.SECONDS)
  private val child: ActorRef = context.actorOf(Props[Child], name = "child")

  override def supervisorStrategy: SupervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case _: CleanRoomException => Restart
    case _: CleanWindowsException => Restart
    case _: CleanBathroomException => Restart
    case _: StandInCornerException => Stop
  }

  def receive = {
    case Play => child ! Play
    case CleanRoom => child ! CleanRoom
    case CleanWindows => child ! CleanWindows
    case CleanBathroom => child ! CleanBathroom
    case StandInCorner => child ! StandInCorner
    case _ => println("Nanny received an invalid message.")
  }
}

class Child extends Actor {
  println(s"Child created: $self")

  def receive = {
    case Play => println("Child happily wishes to play!")
    case CleanRoom => throw new CleanRoomException("Child refuses to clean room!")
    case CleanWindows => throw new CleanWindowsException("Child refuses to clean windows!")
    case CleanBathroom => throw new CleanBathroomException("Child refuses to clean bathroom!")
    case StandInCorner => throw new StandInCornerException("Child refuses to stand in corner!")
    case _ => println("Child received an invalid message.")
  }

  override def preStart(): Unit = {
    super.preStart()
    println("Child pre-start event.")
  }

  override def postStop(): Unit = {
    super.postStop()
    println("Child post-stop event.")
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    super.preRestart(reason, message)
    println(s"Child pre-restart event cause: ${reason.getMessage}.")
  }

  override def postRestart(reason: Throwable): Unit = {
    super.postRestart(reason)
    println(s"Child post-restart event cause: ${reason.getMessage}.")
  }
}

class Watcher extends Actor {
  private implicit val timeout = new Timeout(1, TimeUnit.SECONDS)
  private val futureChild = context.system.actorSelection("/user/nanny/*").resolveOne()
  futureChild onSuccess { case child => context.watch(child)}

  def receive = {
    case Terminated(child) => println(s"Watcher terminated event: ${child.path.name} TERMINATED!")
  }
}

class SupervisorStrategyTest extends FunSuite with BeforeAndAfterAll {
  private implicit val timeout = new Timeout(1, TimeUnit.SECONDS)
  private val system: ActorSystem = ActorSystem.create("funky")
  private val nanny: ActorRef = system.actorOf(Props[Nanny], name = "nanny")
  system.actorOf(Props[Time], name = "watcher")

  override protected def afterAll(): Unit = {
    super.afterAll
    system.shutdown
    system.awaitTermination(3 seconds)
  }

  test("nanny ! child") {
    nanny ! Play
    Thread.sleep(1000)
    nanny ! CleanRoom
    Thread.sleep(1000)
    nanny ! CleanWindows
    Thread.sleep(1000)
    nanny ! CleanBathroom
    Thread.sleep(1000)
    nanny ! StandInCorner
  }
}