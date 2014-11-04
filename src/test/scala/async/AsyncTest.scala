package async

import scala.async.Async._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

import org.scalatest.FunSuite

import rest.AsyncRest

class AsyncTest extends FunSuite {
  private implicit def executor: ExecutionContext = ExecutionContext.global

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
    val future = AsyncRest.asyncJoke
    future onComplete {
      case Success(joke) => assert(!joke.isEmpty)
      case Failure(failure) => throw failure
    }
  }
}