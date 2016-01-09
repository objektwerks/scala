package future

import org.scalatest.FunSuite

import scala.async.Async._
import scala.concurrent.{Future, ExecutionContext}
import scala.util.{Failure, Success}

class AsyncTest extends FunSuite {
  private implicit val ec = ExecutionContext.global

  test("sequential") {
    val future = async {
      val futureOne = async { 1 }
      val futureTwo = async { 2 }
      await(futureOne) + await(futureTwo)
    }
    future onComplete {
      case Success(result) => assert(result == 3)
      case Failure(failure) => throw failure
    }
  }

  test("parallel") {
    val futureOne = async { 1 }
    val futureTwo = async { 2 }
    val futureThree = async {
      await(futureOne) + await(futureTwo)
    }
    futureThree onComplete {
      case Success(result) => assert(result == 3)
      case Failure(failure) => throw failure
    }
  }
}