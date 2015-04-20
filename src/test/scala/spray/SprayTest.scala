package spray

import akka.actor.{Actor, ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import org.specs2.mutable._
import spray.can.Http
import spray.http.MediaTypes._
import spray.routing._
import spray.testkit.Specs2RouteTest

import scala.concurrent.duration._

class RestServiceActor extends Actor with RestService {
  def actorRefFactory = context
  def receive = runRoute(restServiceRoute)
}

trait RestService extends HttpService {
  val restServiceRoute =
    path("") {
      get {
        respondWithMediaType(`text/html`) {
          complete(
            "test"
          )
        }
      }
    }
}

class RestServiceRunner {
  implicit val timeout = Timeout(3.seconds)
  implicit val system = ActorSystem("system")
  val restServiceActor = system.actorOf(Props[RestServiceActor], "rest-service")
  IO(Http) ? Http.Bind(restServiceActor, interface = "localhost", port = 9999)
}

class SprayTest extends Specification with Specs2RouteTest with RestService {
  def restServiceRunner = new RestServiceRunner
  def actorRefFactory = restServiceRunner.system

  "RestService" should {
    "return a text response" in {
      Get() ~> restServiceRoute ~> check {
        println("Calling rest service...")
        responseAs[String] must contain("test")
      }
    }
  }
}