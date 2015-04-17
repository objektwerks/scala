package future

import org.scalatest.FunSuite

import scala.concurrent._
import scala.concurrent.duration._
import scala.util.control.NonFatal
import scala.util.{Failure, Success}

class FutureTest extends FunSuite {
  private implicit val ec = ExecutionContext.global

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
    def send(message: Message): Future[Message] = {
      val promise = Promise[Message] ()
      ec.execute(new Runnable {
        def run() = try {
          promise.success(message)
        } catch {
          case NonFatal(e) => promise.failure(e)
        }
      })
      promise.future
    }
    val future: Future[Message] = send(Message("Hello world!"))
    future onComplete {
      case Success(message) => assert(message.text == "Hello world!")
      case Failure(failure) => throw failure
    }
  }

  test("dependent futures with map") {
    val helloFuture: Future[String] = Future { "Hello" }
    val worldFuture: Future[String] = helloFuture map { s => s + " world!" }
    worldFuture onComplete {
      case Success(success) => assert(success == "Hello world!")
      case Failure(failure) => throw failure
    }
  }

  test("parallel, dependent futures with flat map") {
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
