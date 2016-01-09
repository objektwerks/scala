package future

import org.scalatest.FunSuite

import scala.async.Async._
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class AsyncTest extends FunSuite {
  private implicit val ec = ExecutionContext.global

  test("async") {
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
}