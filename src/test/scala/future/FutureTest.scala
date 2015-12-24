package future

import org.scalatest.FunSuite

import scala.async.Async._
import scala.concurrent._
import scala.concurrent.duration._
import scala.util.control.NonFatal
import scala.util.{Failure, Success}

class FutureTest extends FunSuite {
  private implicit val ec = ExecutionContext.global

  test("blocking future") {
    val future = Future { 1 }
    val result = Await.result(future, 1 second)
    assert(result == 1)
  }

  test("non-blocking future") {
    val future = Future { 1 }
    future onComplete {
      case Success(result) => assert(result == 1)
      case Failure(failure) => throw failure
    }
  }

  test("non-blocking future with promise") {
    def send(message: String): Future[String] = {
      val promise = Promise[String] ()
      ec.execute(new Runnable {
        def run() = try {
          promise.success(message)
        } catch {
          case NonFatal(e) => promise.failure(e)
        }
      })
      promise.future
    }
    val future = send("Hello world!")
    future onComplete {
      case Success(message) => assert(message == "Hello world!")
      case Failure(failure) => throw failure
    }
  }

  test("dependent futures with map") {
    val futureOne = Future { 1 }
    val futureTwo = futureOne map { i => i + 1 }
    futureTwo onComplete {
      case Success(result) => assert(result == 2)
      case Failure(failure) => throw failure
    }
  }

  test("parallel, dependent futures with flat map") {
    val futureOne = Future { 1 }
    val futureTwo = Future { 2 }
    val futureThree = futureOne flatMap {
      one =>
        futureTwo map {
          two => one + two
        }
    }
    futureThree onComplete {
      case Success(result) => assert(result == 3)
      case Failure(failure) => throw failure
    }
  }

  test("parallel futures with for comprehension") {
    val futureOne = Future { 1 }
    val futureTwo = Future { 2 }
    val futureThree = for {
      one <- futureOne
      two <- futureTwo
    } yield one + two
    futureThree onComplete {
      case Success(result) => assert(result == 3)
      case Failure(failure) => throw failure
    }
  }

  test("sequential futures with for comprehension") {
    val future = for {
      one <-  Future { 1 }
      two <- Future { 2 }
    } yield one + two
    future onComplete {
      case Success(result) => assert(result == 3)
      case Failure(failure) => throw failure
    }
  }

  test("future sequence") {
    val listOfFutures = List(Future(1), Future(2))
    val futureOfList = Future.sequence(listOfFutures)
    val future = futureOfList.map(_.sum)
    future onComplete {
      case Success(result) => assert(result == 3)
      case Failure(failure) => throw failure
    }
  }

  test("future traverse") {
    val futureOfList = Future.traverse((1 to 2).toList) (i => Future(i * 1))
    val future = futureOfList.map(_.sum)
    future onComplete {
      case Success(result) => assert(result == 3)
      case Failure(failure) => throw failure
    }
  }

  test("future fold") {
    val listOfFutures = for (i <- 1 to 2) yield Future(i * 1)
    val future = Future.fold(listOfFutures) (0) (_ + _) // reduce without (0) arg yields identical result
    future onComplete {
      case Success(result) => assert(result == 3)
      case Failure(failure) => throw failure
    }
  }

  test("future andThen") {
    val future: Future[Int] = Future(Integer.parseInt("1")) andThen { case Success(result) => println(s"side-effecting andThen output: $result") }
    future onComplete {
      case Success(result) => assert(result == 1)
      case Failure(failure) => throw failure
    }
  }

  test("future recover") {
    val future = Future(Integer.parseInt("one")) recover { case t: Throwable => 0 }
    future onComplete {
      case Success(result) => assert(result == 0)
      case Failure(failure) => throw failure
    }
  }

  test("future fallbackTo") {
    val future = Future(Integer.parseInt("one")) fallbackTo Future(0)
    future onComplete {
      case Success(result) => assert(result == 0)
      case Failure(failure) => throw failure
    }
  }

  test("future zip") {
    val future = Future(1) zip Future(2) map { case (x, y) => x + y }
    future onComplete  {
      case Success(result) => assert(result == 3)
      case Failure(failure) => throw failure
    }
  }

  test("async") {
    val future = async {
      val futureOne: Future[Int] = async { 1 }
      val futureTwo: Future[Int] = async { 2 }
      await(futureOne) + await(futureTwo)
    }
    future onComplete {
      case Success(result) => assert(result == 3)
      case Failure(failure) => throw failure
    }
  }
}