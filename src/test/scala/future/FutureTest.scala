package future

import java.util.concurrent.TimeUnit

import org.scalatest.FunSuite

import scala.async.Async.{async, await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

class FutureTest extends FunSuite {
  test("async") {
    val future: Future[Int] = async {
      val futureOne: Future[Int] = async { 1 }
      val futureTwo: Future[Int] = async { 2 }
      await(futureOne) + await(futureTwo)
    }
    future onComplete {
      case Success(result) => assert(result == 3)
      case Failure(failure) => throw failure
    }
  }

  test("anonymous blocking future with implicit promise") {
    val future: Future[String] = Future {
      "Hello world!"
    }
    val result = Await.result(future, Duration(1, TimeUnit.SECONDS))
    assert(result.equals("Hello world!"))
  }

  test("anonymous non-blocking future with implicit promise") {
    val future: Future[String] = Future {
      "Hello world!"
    }
    future onComplete {
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
    val futureOne: Future[String] = Future {
      "Hello"
    }
    val futureTwo: Future[String] = futureOne map {
      s => s + " world!"
    }
    futureTwo onComplete {
      case Success(success) => assert(success == "Hello world!")
      case Failure(failure) => throw failure
    }
  }

  test("anonymous non-blocking future with monadic flat map") {
    val futureOne: Future[String] = Future {
      "Hello"
    }
    val futureTwo: Future[String] = Future {
      " world"
    }
    val futureThree: Future[String] = futureOne flatMap {
      partOne =>
        futureTwo map {
          partTwo => partOne + partTwo + "!"
        }
    }
    futureThree onComplete {
      case Success(success) => assert(success == "Hello world!")
      case Failure(failure) => throw failure
    }
  }

  test("anonymous non-blocking future with for comprehension") {
    val futureOne: Future[String] = Future {
      "Hello"
    }
    val futureTwo: Future[String] = Future {
      " world"
    }
    val futureThree: Future[String] = for {
      partOne <- futureOne
      partTwo <- futureTwo
    } yield partOne + partTwo + "!"

    futureThree onComplete {
      case Success(success) => assert(success == "Hello world!")
      case Failure(failure) => throw failure
    }
  }
}