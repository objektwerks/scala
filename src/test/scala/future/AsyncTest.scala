package future

import org.scalatest.FunSuite

import scala.async.Async._
import scala.concurrent.ExecutionContext.Implicits.global

class AsyncTest extends FunSuite {
  test("sequential") {
    val future = async {
      val futureOne = async { 1 }
      val futureTwo = async { 2 }
      await(futureOne) + await(futureTwo)
    }
    future foreach { _ == 3 }
  }

  test("parallel") {
    val futureOne = async { 1 }
    val futureTwo = async { 2 }
    val futureThree = async {
      await(futureOne) + await(futureTwo)
    }
    futureThree foreach { _ == 3 }
  }
}