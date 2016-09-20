package future

import org.scalatest.FunSuite

import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

class FutureTest extends FunSuite {
  implicit val ec = ExecutionContext.global

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

  test("non-blocking promise > future") {
    def send(message: String): Future[String] = {
      val promise = Promise[String] ()
      val fn = new Thread(() => Try(promise.success(message)).recover { case e => promise.failure(e) } )
      ec.execute(fn)
      promise.future
    }
    val future = send("Hello world!")
    future onComplete {
      case Success(message) => assert(message == "Hello world!")
      case Failure(failure) => throw failure
    }
  }

  test("sequential futures with map") {
    val futureOne = Future { 1 }
    val futureTwo = futureOne map { i => i + 1 }
    futureTwo onComplete {
      case Success(result) => assert(result == 2)
      case Failure(failure) => throw failure
    }
  }

  test("parallel futures with flatmap") {
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

  test("futures sequence") {
    val sequence = Future.sequence(List(Future(1), Future(2)))
    val future = sequence.map(_.sum)
    future onComplete {
      case Success(result) => assert(result == 3)
      case Failure(failure) => throw failure
    }
  }

  test("futures traverse") {
    val traversal = Future.traverse((1 to 2).toList) (i => Future(i * 1))
    val future = traversal.map(_.sum)
    future onComplete {
      case Success(result) => assert(result == 3)
      case Failure(failure) => throw failure
    }
  }

  test("future andThen") {
    val future = Future(Integer.parseInt("1")) andThen { case Success(i) => println("Execute 'andThen' side-effecting code!") }
    future onComplete {
      case Success(result) => assert(result == 1)
      case Failure(failure) => throw failure
    }
  }

  test("futures > zip > map") {
    val future = Future(1) zip Future(2) map { case (x, y) => x + y }
    future onComplete  {
      case Success(result) => assert(result == 3)
      case Failure(failure) => throw failure
    }
  }

  test("future recover") {
    val future = Future(Integer.parseInt("one")) recover { case t: Throwable => 1 }
    future onComplete {
      case Success(result) => assert(result == 1)
      case Failure(failure) => throw failure
    }
  }

  test("future fallbackTo") {
    val future = Future(Integer.parseInt("one")) fallbackTo Future(1)
    future onComplete {
      case Success(result) => assert(result == 1)
      case Failure(failure) => throw failure
    }
  }

  test("future fail fast") {
    val future = for {
      x <- Future { Integer.parseInt("one") }
      y <- Future { Integer.parseInt("2") }
      z <- Future { Integer.parseInt("3") }
    } yield (x, y, z)
    future onComplete {
      case Success(result) => throw new IllegalStateException("Fail fast failed!")
      case Failure(failure) => assert(failure.isInstanceOf[NumberFormatException])
    }
  }

  test("future sequence fail fast ") {
    val sequence = Future.sequence(List(Future(Integer.parseInt("one")), Future(Integer.parseInt("2"))))
    val future = sequence map(_.sum)
    future onComplete {
      case Success(result) => throw new IllegalStateException("Fail fast failed!")
      case Failure(failure) => assert(failure.isInstanceOf[NumberFormatException])
    }
  }

  test("future traverse fail fast") {
    val traversal = Future.traverse((1 to 2).toList) (i => Future(i / 0))
    val future = traversal.map { i => println(s"Never executes: $i"); i.sum }
    future onComplete {
      case Success(result) => throw new IllegalStateException("Fail fast failed!")
      case Failure(failure) => assert(failure.isInstanceOf[ArithmeticException])
    }
  }
}