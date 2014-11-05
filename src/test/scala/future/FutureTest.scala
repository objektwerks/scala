package future

import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Success}

import org.scalatest.FunSuite

class FutureTest extends FunSuite {
  private implicit def executor: ExecutionContext = ExecutionContext.global

  test("blocking future") {
    val future: Future[String] = Future { "Hello world!" }
    val result: String = Await.result(future, 1 second)
    assert(result == "Hello world!")
  }

  test("non-blocking future") {
    val helloWorldFuture: Future[String] = Future { "Hello world!" }
    helloWorldFuture onComplete {
      case Success(success) => assert(success == "Hello world!")
      case Failure(failure) => throw failure
    }
  }

  test("non-blocking future with promise") {
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

  test("parallel futures with map") {
    val helloFuture: Future[String] = Future { "Hello" }
    val worldFuture: Future[String] = helloFuture map { s => s + " world!" }
    worldFuture onComplete {
      case Success(success) => assert(success == "Hello world!")
      case Failure(failure) => throw failure
    }
  }

  test("parallel futures with flat map") {
    val helloFuture: Future[String] = Future { "Hello" }
    val worldFuture: Future[String] = Future { " world!" }
    val helloWorldFuture: Future[String] = helloFuture flatMap {
      hello =>
        worldFuture map {
          world => hello + world
        }
    }
    helloWorldFuture onComplete {
      case Success(success) => assert(success == "Hello world!")
      case Failure(failure) => throw failure
    }
  }

  test("parallel futures with for comprehension") {
    val helloFuture: Future[String] = Future { "Hello" }
    val worldFuture: Future[String] = Future { " world!" }
    val helloWorldFuture: Future[String] = for {
      hello <- helloFuture
      world <- worldFuture
    } yield hello + world
    helloWorldFuture onComplete {
      case Success(success) => assert(success == "Hello world!")
      case Failure(failure) => throw failure
    }
  }

  test("sequential futures with for comprehension") {
    val helloWorldFuture: Future[String] = for {
      hello <-  Future { "Hello" }
      world <- Future { " world!" }
    } yield hello + world
    helloWorldFuture onComplete {
      case Success(success) => assert(success == "Hello world!")
      case Failure(failure) => throw failure
    }
  }
}