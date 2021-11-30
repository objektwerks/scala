package objektwerks.future

import org.scalatest.funsuite.AsyncFunSuite
import org.scalatest.matchers.should.Matchers

import scala.util.{Failure, Success, Try}
import scala.concurrent.{Future, Promise}

class FutureTest extends AsyncFunSuite with Matchers {
  test("promise") {
    def send(message: String): Future[String] = {
      val promise = Promise[String] ()
      val runnable = new Runnable {
        override def run(): Unit = {
          promise.success(message)
        }
      }
      executionContext.execute(runnable)
      promise.future
    }
    send("Hello world!") map { _ shouldBe "Hello world!" }
  }

  test("sequential") {
    val future = for {
      one <-  Future(1)
      two <- Future(2)
    } yield one + two
    future map { _ shouldBe 3 }
  }

  test("parallel") {
    val futureOne = Future(1)
    val futureTwo = Future(2)
    val futureThree = for {
      one <- futureOne
      two <- futureTwo
    } yield one + two
    futureThree map { _ shouldBe 3 }
  }

  test("sequential fail fast") {
    recoverToSucceededIf[NumberFormatException] {
      for {
        x <- Future { Integer.parseInt("one") }
        y <- Future { Integer.parseInt("2") }
        z <- Future { Integer.parseInt("3") }
      } yield (x, y, z)
    }
  }

  test("parallel fail fast") {
    recoverToSucceededIf[NumberFormatException] {
      val futureOne = Future { Integer.parseInt("one") }
      val futureTwo = Future { Integer.parseInt("2") }
      val futureThree = Future { Integer.parseInt("3") }
      for {
        x <- futureOne
        y <- futureTwo
        z <- futureThree
      } yield (x, y, z)
    }
  }

  test("sequence") {
    val futureOfListOfInt = Future.sequence(List(Future(1), Future(2)))
    val futureOfInt = futureOfListOfInt map { _.sum }
    futureOfInt map { _ shouldBe 3 }
  }

  test("traverse") {
    val futureOfListOfInt = Future.traverse((1 to 2).toList) (i => Future(i * 1))
    val futureOfInt = futureOfListOfInt map { _.sum }
    futureOfInt map { _ shouldBe 3 }
  }

  test("sequence fail fast ") {
    recoverToSucceededIf[NumberFormatException] {
      val futureOfListOfInt = Future.sequence(List(Future(Integer.parseInt("one")), Future(Integer.parseInt("2"))))
      futureOfListOfInt map { _.sum }
    }
  }

  test("traverse fail fast") {
    recoverToSucceededIf[ArithmeticException] {
      val futureOfListOfInt = Future.traverse((1 to 2).toList) (i => Future(i / 0))
      futureOfListOfInt map { _.sum }
    }
  }

  test("collect") {
    Future(3) collect { case i => i shouldEqual 3 }
  }

  test("filter") {
    Future(3) filter { _ == 3 } map { _ shouldBe 3 }
  }

  test("foldLeft") {
    val ListFutureOfInt = List(Future(1), Future(2))
    val futureOfInt = Future.foldLeft(ListFutureOfInt)(0){ (acc, num) => acc + num }
    futureOfInt map { _ shouldBe 3 }
  }

  test("reduceLeft") {
    val ListFutureOfInt = List(Future(1), Future(2))
    val futureOfInt = Future.reduceLeft(ListFutureOfInt){ (acc, num) => acc + num }
    futureOfInt map { _ shouldBe 3 }
  }

  test("fallbackTo") {
    val future = Future(Integer.parseInt("three")) fallbackTo Future(3)
    future map { _ shouldBe 3 }
  }

  test("fromTry") {
    val future = Future.fromTry(Try(Integer.parseInt("3")))
    future map { _ shouldBe 3 }
  }

  test("andThen") {
    val future = Future(Integer.parseInt("3")) andThen { case Success(_) => println("Execute 'andThen' side-effecting code!") }
    future map { _ shouldBe 3 }
  }

  test("zip > map") {
    val future = Future(1) zip Future(2) map { case (x, y) => x + y }
    future map { _ shouldBe 3 }
  }

  test("recover") {
    val future = Future(Integer.parseInt("three")) recover { case _ => 3 }
    future map { _ shouldBe 3 }
  }

  test("map > recover") {
    val future = Future(Integer.parseInt("one")) map { _ * 3 } recover { case _ => -1 }
    future map { _ shouldBe -1 }
 }

  test("recoverWith") {
    val future = Future(Integer.parseInt("three")) recoverWith { case _ => Future(3) }
    future map { _ shouldBe 3 }
  }

  test("for > recover") {
    val future = Future(Integer.parseInt("three"))
    val result = (
      for {
        i <- future
      } yield i
    ).recover { case _: Throwable => -1 }
    result map { _ shouldBe -1 }
  }

  test("transform") {
    val future = Future(Integer.parseInt("1")).transform(_ + 2, failure => failure)
    future map { _ shouldBe 3 }

    recoverToSucceededIf[NumberFormatException] {
      Future(Integer.parseInt("one")).transform(_ + 2, failure => failure)
    }
  }

  test("transformWith") {
    val futureOne = Future { Integer.parseInt("1") } transformWith {
      case Success(i) => Future(i)
      case Failure(_) => Future(-1)
    }
    futureOne map { _ shouldBe 1 }

    val futureNegativeOne = Future { Integer.parseInt("one") } transformWith {
      case Success(i) => Future(i)
      case Failure(_) => Future(-1)
    }
    futureNegativeOne map { _ shouldBe -1 }
  }

  test("flatten") {
    Future{ Future(1) }.flatten map { _ shouldBe 1 }
  }

  test("zipWith") {
    val future = Future("My average is:").zipWith(Future(100.0)) { case (label, average) => s"$label $average" }
    future map { _ shouldBe "My average is: 100.0" }
  }
}