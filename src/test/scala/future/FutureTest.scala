package future

import java.util.concurrent.TimeUnit

import org.scalatest.FunSuite

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

class FutureTest extends FunSuite {
  test("anonymous blocking future with implicit promise") {
    val f: Future[String] = Future {
      "Hello world!"
    }
    val r = Await.result(f, Duration(1, TimeUnit.SECONDS))
    assert(r.equals("Hello world!"))
  }

  test("anonymous non-blocking future with implicit promise") {
    val f: Future[String] = Future {
      "Hello world!"
    }
    f onComplete {
      case Success(r) => assert(r.equals("Hello world!"))
      case Failure(e) => throw e
    }
  }

  test("anonymous non-blocking future with explicit promise") {
    case class Message(text: String)
    def send(promise: Promise[Message], message: Message): Future[Message] = {
      promise.success(message)
      promise.future
    }
    val f: Future[Message] = send(Promise[Message](), Message("Hello world!"))
    f onComplete {
      case Success(m) => assert(m.text == "Hello world!")
      case Failure(e) => throw e
    }
  }

  test("anonymous non-blocking future with monadic map") {
    val fPartOne: Future[String] = Future {
      "Hello"
    }
    val fPartTwo: Future[String] = fPartOne map {
      s => s + " world!"
    }
    fPartTwo onComplete {
      case Success(s) => assert(s == "Hello world!")
      case Failure(e) => throw e
    }
  }

  test("anonymous non-blocking future with monadic flat map") {
    val fPartOne: Future[String] = Future {
      "Hello"
    }
    val fPartTwo: Future[String] = Future {
      " world"
    }
    val fPartThree: Future[String] = fPartOne flatMap {
      partOne =>
        fPartTwo map {
          partTwo => partOne + partTwo + "!"
        }
    }
    fPartThree onComplete {
      case Success(s) => assert(s == "Hello world!")
      case Failure(e) => throw e
    }
  }

  test("anonymous non-blocking future with for comprehension") {
    val fPartOne: Future[String] = Future {
      "Hello"
    }
    val fPartTwo: Future[String] = Future {
      " world"
    }
    val fPartThree: Future[String] = for {
      partOne <- fPartOne
      partTwo <- fPartTwo
    } yield partOne + partTwo + "!"

    fPartThree onComplete {
      case Success(s) => assert(s == "Hello world!")
      case Failure(e) => throw e
    }
  }
}