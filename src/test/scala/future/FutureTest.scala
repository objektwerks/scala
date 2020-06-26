package future

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Success, Try}

class FutureTest extends AnyFunSuite with Matchers {
  test("blocking") {
    Await.result(Future(1), 1 second) shouldEqual 1
  }

  test("non-blocking") {
    Future(1) foreach { i => i shouldEqual 1 }
  }

  test("promise") {
    def send(message: String): Future[String] = {
      val promise = Promise[String] ()
      val fn = new Thread(() => promise.success(message))
      global.execute(fn)
      promise.future
    }
    val future = send("Hello world!")
    future foreach { s => s shouldEqual "Hello world!" }
  }

  test("sequential") {
    val future = for {
      one <-  Future(1)
      two <- Future(2)
    } yield one + two
    future foreach { i => i shouldEqual 3 }
  }

  test("parallel") {
    val futureOne = Future(1)
    val futureTwo = Future(2)
    val futureThree = for {
      one <- futureOne
      two <- futureTwo
    } yield one + two
    futureThree foreach { i => i shouldEqual 3 }
  }

  test("sequential fail fast") {
    val future = for {
      x <- Future { Integer.parseInt("one") }
      y <- Future { Integer.parseInt("2") }
      z <- Future { Integer.parseInt("3") }
    } yield (x, y, z)
    future onComplete {
      case Success(result) => throw new IllegalStateException(s"Fail fast failed: $result")
      case Failure(failure) => failure.isInstanceOf[NumberFormatException] shouldBe true
    }
  }

  test("parallel fail fast") {
    val futureOne = Future { Integer.parseInt("one") }
    val futureTwo = Future { Integer.parseInt("2") }
    val futureThree = Future { Integer.parseInt("3") }
    val future = for {
      x <- futureOne
      y <- futureTwo
      z <- futureThree
    } yield (x, y, z)
    future onComplete {
      case Success(result) => throw new IllegalStateException(s"Fail fast failed: $result")
      case Failure(failure) => failure.isInstanceOf[NumberFormatException] shouldBe true
    }
  }

  test("sequence") {
    val futureOfListOfInt = Future.sequence(List(Future(1), Future(2)))
    val futureOfInt = futureOfListOfInt.map(_.sum)
    futureOfInt foreach { i => i shouldEqual 3 }
  }

  test("traverse") {
    val futureOfListOfInt = Future.traverse((1 to 2).toList) (i => Future(i * 1))
    val futureOfInt = futureOfListOfInt.map(_.sum)
    futureOfInt foreach { i => i shouldEqual 3 }
  }

  test("sequence fail fast ") {
    val futureOfListOfInt = Future.sequence(List(Future(Integer.parseInt("one")), Future(Integer.parseInt("2"))))
    val futureOfInt = futureOfListOfInt map(_.sum)
    futureOfInt onComplete {
      case Success(result) => throw new IllegalStateException(s"Fail fast failed: $result")
      case Failure(failure) => failure.isInstanceOf[NumberFormatException] shouldBe true
    }
  }

  test("traverse fail fast") {
    val futureOfListOfInt = Future.traverse((1 to 2).toList) (i => Future(i / 0))
    val futureOfInt = futureOfListOfInt.map { i => println(s"Never executes: $i"); i.sum }
    futureOfInt onComplete {
      case Success(result) => throw new IllegalStateException(s"Fail fast failed: $result")
      case Failure(failure) => failure.isInstanceOf[ArithmeticException] shouldBe true
    }
  }

  test("collect") {
    Future(3) collect { case i => i shouldEqual 3 }
  }

  test("filter") {
    Future(3) filter { _ == 3 } foreach { i => i shouldEqual 3 }
  }

  test("foldLeft") {
    val ListFutureOfInt = List(Future(1), Future(2))
    val futureOfInt = Future.foldLeft(ListFutureOfInt)(0){ (acc, num) => acc + num }
    futureOfInt foreach { i => i shouldEqual 3 }
  }

  test("reduceLeft") {
    val ListFutureOfInt = List(Future(1), Future(2))
    val futureOfInt = Future.reduceLeft(ListFutureOfInt){ (acc, num) => acc + num }
    futureOfInt foreach { i => i shouldEqual 3 }
  }

  test("foreach") {
    Future(3) foreach { i => i shouldEqual 3 }
  }

  test("fallbackTo") {
    Future(Integer.parseInt("one")) fallbackTo Future(1) foreach { i => i shouldEqual 1 }
  }

  test("fromTry") {
    Future.fromTry(Try(Integer.parseInt("3"))) foreach { i => i shouldEqual 3 }
  }

  test("andThen") {
    Future(Integer.parseInt("1")) andThen { case Success(_) => println("Execute 'andThen' side-effecting code!") } foreach { i => i shouldEqual 1 }
  }

  test("failed") {
    Future.failed[Exception](new Exception("failed")).foreach { e => e.getMessage shouldEqual "failed" }
  }

  test("successful") {
    Future.successful[Int](3).foreach { i => i shouldEqual 3 }
  }

  test("zip > map") {
    Future(1) zip Future(2) map { case (x, y) => x + y } foreach { i => i shouldEqual 3 }
  }

  test("recover") {
    Future(Integer.parseInt("one")) recover { case _ => 1 } foreach { i => i shouldEqual 1 }
  }

  test("recoverWith") {
    Future(Integer.parseInt("one")) recoverWith { case _ => Future(1) } foreach { i => i shouldEqual 1 }
  }

  test("recover for") {
    val future = Future(Integer.parseInt("one"))
    val result = (
      for {
        i <- future
      } yield i
    ).recover { case _: Throwable => -1 }
    result foreach { i => i shouldEqual -1 }
  }

  test("transform") {
    Future(Integer.parseInt("1")).transform(i => i + 2, failure => new Exception("failure", failure)) foreach { i => i shouldEqual 3 }
    Future(Integer.parseInt("one")).transform(i => i + 2, failure => new Exception("failure", failure)) foreach { i => i should not equal 3 }
  }

  test("transformWith") {
    Future { Integer.parseInt("1") } transformWith {
      case Success(i) => Future(i)
      case Failure(_) => Future(-1)
    } foreach { i => i shouldEqual 1 }

    Future { Integer.parseInt("one") } transformWith {
      case Success(i) => Future(i)
      case Failure(_) => Future(-1)
    } foreach { i => i shouldEqual -1  }
  }

  test("flatten") {
    Future { Future(1) }.flatten foreach { i => i shouldEqual 1 }
  }

  test("zipWith") {
    Future("My average is:")
      .zipWith(Future(100.0)) { case (label, average) => s"$label $average" }
      .foreach { s => s shouldEqual "My average is: 100.0" }
  }
}