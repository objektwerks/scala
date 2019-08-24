package future

import org.scalatest.{FunSuite, Matchers}

import scala.async.Async._
import scala.concurrent.ExecutionContext.Implicits.global

class AsyncTest extends FunSuite with Matchers {
  test("sequential") {
    val future = async {
      val futureOne = async { 1 }
      val futureTwo = async { 2 }
      await(futureOne) + await(futureTwo)
    }
    future foreach { i => i shouldEqual 3 }
  }

  test("parallel") {
    val futureOne = async { 1 }
    val futureTwo = async { 2 }
    val futureThree = async {
      await(futureOne) + await(futureTwo)
    }
    futureThree foreach { i => i shouldEqual 3 }
  }
}