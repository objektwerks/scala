package spray

import akka.actor.{Actor, ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import org.specs2.mutable._
import spray.can.Http
import spray.http.MediaTypes
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
        respondWithMediaType(MediaTypes.`application/json`) {
          complete(Message("get rest message"))
        }
      } ~
      post {
        respondWithMediaType(MediaTypes.`application/json`) {
          complete(Message("post rest message"))
        }
      } ~
      put {
        respondWithMediaType(MediaTypes.`application/json`) {
          complete(Message("put rest message"))
        }
      } ~
      delete {
        respondWithMediaType(MediaTypes.`application/json`) {
          complete(Message("delete rest message"))
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
    "handle Get, Post, Put and Delete json Messages." in {
      Get() ~> restServiceRoute ~> check {
        responseAs[Message] === Message("get rest message")
      }
      Post() ~> restServiceRoute ~> check {
        responseAs[Message] === Message("post rest message")
      }
      Put() ~> restServiceRoute ~> check {
        responseAs[Message] === Message("put rest message")
      }
      Delete() ~> restServiceRoute ~> check {
        responseAs[Message] === Message("delete rest message")
      }
    }
  }
}