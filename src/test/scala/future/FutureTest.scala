package future

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Success}

import org.scalatest.FunSuite

class FutureTest extends FunSuite {
  test("anonymous blocking future with implicit promise") {
    val future: Future[String] = Future {
      "Hello world!"
    }
    val result = Await.result(future, 1 second)
    assert(result.equals("Hello world!"))
  }

  test("anonymous non-blocking future with implicit promise") {
    val helloWorldFuture: Future[String] = Future {
      "Hello world!"
    }
    helloWorldFuture onComplete {
      case Success(result) => assert(result.equals("Hello world!"))
      case Failure(failure) => throw failure
    }
  }

  test("anonymous non-blocking future with explicit promise") {
    case class Message(text: String)
    def send(promise: Promise[Message], message: Message): Future[Message] = {
      promise.success(message)
      promise.future
    }
    val future: Future[Message] = send(Promise[Message](), Message("Hello world!"))
    future onComplete {
      case Success(message) => assert(message.text == "Hello world!")
      case Failure(failure) => throw failure
    }
  }

  test("anonymous non-blocking future with monadic map") {
    val helloFuture: Future[String] = Future {
      "Hello"
    }
    val worldFuture: Future[String] = helloFuture map {
      s => s + " world!"
    }
    def assertHelloWorldFuture(text: String) = {
      assert(text == "Hello world!")
    }
    worldFuture onComplete {
      case Success(success) => assertHelloWorldFuture(success)
      case Failure(failure) => throw failure
    }
  }

  test("anonymous non-blocking future with monadic flat map") {
    val helloFuture: Future[String] = Future {
      "Hello"
    }
    val worldFuture: Future[String] = Future {
      " world"
    }
    val helloWorldFuture: Future[String] = helloFuture flatMap {
      hello =>
        worldFuture map {
          world => hello + world + "!"
        }
    }
    def checkHelloWorldFutures(text: String) = {
      assert(text == "Hello world!")
    }
    helloWorldFuture onComplete {
      case Success(success) => checkHelloWorldFutures(success)
      case Failure(failure) => throw failure
    }
  }

  test("anonymous non-blocking future with for comprehension") {
    val helloFuture: Future[String] = Future {
      "Hello"
    }
    val worldFuture: Future[String] = Future {
      " world"
    }
    val helloWorldFuture: Future[String] = for {
      hello <- helloFuture
      world <- worldFuture
    } yield hello + world + "!"
    def checkHelloWorldFutures(text: String) = {
      assert(text == "Hello world!")
    }
    helloWorldFuture onComplete {
      case Success(success) => checkHelloWorldFutures(success)
      case Failure(failure) => throw failure
    }
  }
}