package async

import org.scalatest.FunSuite

import scala.async.Async._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

class AsyncTest extends FunSuite {
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

  test("async rest") {
    val joke = Await.result(AsyncRest.asyncJoke, 3 seconds)
    assert(!joke.isEmpty)
    println(joke)
  }
}