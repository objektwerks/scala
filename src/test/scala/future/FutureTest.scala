package future

import org.scalatest.FunSuite

import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

class FutureTest extends FunSuite {
  implicit val ec = ExecutionContext.global

  test("blocking") {
    val future = Future(1)
    val result = Await.result(future, 1 second)
    assert(result == 1)
  }

  test("non-blocking") {
    Future(1) foreach { x => assert(x == 1) }
  }

  test("non-blocking promise") {
    def send(message: String): Future[String] = {
      val promise = Promise[String] ()
      val fn = new Thread(() => Try(promise.success(message)).recover { case e => promise.failure(e) } )
      ec.execute(fn)
      promise.future
    }
    val future = send("Hello world!")
    future foreach { m => assert(m == "Hello world!")}
  }

  test("sequential map") {
    val futureOne = Future { 1 }
    val futureTwo = futureOne map { i => i + 1 }
    futureTwo foreach { x => assert(x == 2) }
  }

  test("parallel flatmap") {
    val futureOne = Future { 1 }
    val futureTwo = Future { 2 }
    val futureThree = futureOne flatMap {
      one =>
        futureTwo map {
          two => one + two
        }
    }
    futureThree foreach { x => assert(x == 3) }
  }

  test("sequential for") {
    val future = for {
      one <-  Future { 1 }
      two <- Future { 2 }
    } yield one + two
    future foreach { x => assert(x == 3) }
  }

  test("parallel for") {
    val futureOne = Future { 1 }
    val futureTwo = Future { 2 }
    val futureThree = for {
      one <- futureOne
      two <- futureTwo
    } yield one + two
    futureThree foreach { x => assert(x == 3) }
  }

  test("fail fast") {
    val future = for {
      x <- Future { Integer.parseInt("one") }
      y <- Future { Integer.parseInt("2") }
      z <- Future { Integer.parseInt("3") }
    } yield (x, y, z)
    future onComplete {
      case Success(result) => throw new IllegalStateException(s"Fail fast failed: $result")
      case Failure(failure) => assert(failure.isInstanceOf[NumberFormatException])
    }
  }

  test("sequence") {
    val sequence = Future.sequence(List(Future(1), Future(2)))
    val future = sequence.map(_.sum)
    future foreach { x => assert(x == 3) }
  }

  test("traverse") {
    val traversal = Future.traverse((1 to 2).toList) (i => Future(i * 1))
    val future = traversal.map(_.sum)
    future foreach { x => assert(x == 3) }
  }

  test("sequence fail fast ") {
    val sequence = Future.sequence(List(Future(Integer.parseInt("one")), Future(Integer.parseInt("2"))))
    val future = sequence map(_.sum)
    future onComplete {
      case Success(result) => throw new IllegalStateException(s"Fail fast failed: $result")
      case Failure(failure) => assert(failure.isInstanceOf[NumberFormatException])
    }
  }

  test("traverse fail fast") {
    val traversal = Future.traverse((1 to 2).toList) (i => Future(i / 0))
    val future = traversal.map { i => println(s"Never executes: $i"); i.sum }
    future onComplete {
      case Success(result) => throw new IllegalStateException(s"Fail fast failed: $result")
      case Failure(failure) => assert(failure.isInstanceOf[ArithmeticException])
    }
  }

  test("collect") {
    Future(3) collect { case value => assert(value == 3) }
  }

  test("filter") {
    Future(3) filter { _ == 3 } foreach { x => assert(x == 3) }
  }

  test("fold") {
    val futures = List(Future(1), Future(2))
    val future = Future.foldLeft(futures)(0){ (acc, num) => acc + num }
    future foreach { x => assert(x == 3) }
  }

  test("reduce") {
    val futures = List(Future(1), Future(2))
    val future = Future.reduceLeft(futures){ (acc, num) => acc + num }
    future foreach { x => assert(x == 3) }
  }

  test("foreach") {
    Future(3) foreach { x => assert(x == 3) }
  }

  test("fallbackTo") {
    Future(Integer.parseInt("one")) fallbackTo Future(1) foreach { x => assert(x == 1) }
  }

  test("fromTry") {
    Future.fromTry(Try(Integer.parseInt("3"))) foreach { x => assert(x == 3) }
  }

  test("andThen") {
    Future(Integer.parseInt("1")) andThen { case Success(_) => println("Execute 'andThen' side-effecting code!") } foreach { x => assert(x == 1) }
  }

  test("failed") {
    Future.failed[Exception](new Exception("failed")).foreach { e => assert(e.getMessage == "failed") }
  }

  test("successful") {
    Future.successful[Int](3).foreach { x => assert(x == 3) }
  }

  test("zip map") {
    Future(1) zip Future(2) map { case (x, y) => x + y } foreach { x => assert(x == 3) }
  }

  test("recover") {
    Future(Integer.parseInt("one")) recover { case _ => 1 } foreach { x => assert(x == 1) }
  }

  test("for comprehension with recover") {
    val future = Future(Integer.parseInt("one"))
    val result = ( for { i <- future } yield i ).recover { case _: Throwable => -1 }
    result foreach { x => assert(x == -1) }
  }

  test("recoverWith") {
    Future(Integer.parseInt("one")) recoverWith { case _ => Future { 1 } } foreach { x => assert(x == 1) }
  }

  test("transform") {
    Future(Integer.parseInt("1")) transform(result => result + 2, failure => new Exception("failure", failure)) foreach { x => assert(x == 3) }
    Future(Integer.parseInt("one")) transform(result => result + 2, failure => new Exception("failure", failure)) foreach { x => assert(x != 3) }
  }

  test("transformWith") {
    Future { Integer.parseInt("1") } transformWith {
      case Success(result) => Future { result }
      case Failure(_) => Future { -1 }
    } foreach { x => assert(x == 1) }
    Future { Integer.parseInt("one") } transformWith {
      case Success(result) => Future { result }
      case Failure(_) => Future { -1 }
    } foreach { x => assert(x == -1) }
  }

  test("flatten") {
    Future { Future { 1 } }.flatten foreach { x => assert(x == 1) }
  }

  test("zipWith") {
    Future("My average is:").zipWith(Future(100.0)) { case (label, average) => s"$label $average" } foreach { answer => assert(answer == "My average is: 100.0") }
  }
}