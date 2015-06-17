package spray

import akka.actor.{Actor, ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import org.specs2.mutable._
import spray.can.Http
import spray.http.MediaTypes._
import spray.json._
import spray.routing._
import spray.testkit.Specs2RouteTest

import scala.concurrent.duration._

case class Message(text: String)

trait RestService extends HttpService with DefaultJsonProtocol {
  implicit val messageFormat = jsonFormat1(Message)

  val restServiceRoute = {
    import spray.httpx.SprayJsonSupport._
    path("") {
      get {
        respondWithMediaType(`application/json`) {
          complete {
            Message("test")
          }
        }
      }
    }
  }
}

class RestServiceActor extends RestService with Actor {
  def actorRefFactory = context
  def receive = runRoute(restServiceRoute)
}

class RestServiceRunner {
  implicit val timeout = Timeout(3.seconds)
  implicit val system = ActorSystem("system")
  val restServiceActor = system.actorOf(Props[RestServiceActor], "rest-service-actor")
  IO(Http) ? Http.Bind(restServiceActor, interface = "localhost", port = 9999)
}

class SprayTest extends Specification with Specs2RouteTest with RestService {
  def restServiceRunner = new RestServiceRunner
  def actorRefFactory = restServiceRunner.system
  import spray.httpx.SprayJsonSupport._

  "RestService" should {
    "return a text response" in {
      Get() ~> restServiceRoute ~> check {
        responseAs[Message] === Message("test")
      }
    }
  }
}