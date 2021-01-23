package future

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.async.Async._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AsyncTest extends AnyFunSuite with Matchers {
  test("sequential") {
    val future: Future[Int] = async {
      val futureOne: Future[Int] = async { 1 }
      val futureTwo: Future[Int] = async { 2 }
      await(futureOne) + await(futureTwo)
    }
    future foreach { _ shouldEqual 3 }
  }

  test("parallel") {
    val futureOne: Future[Int] = async { 1 }
    val futureTwo: Future[Int] = async { 2 }
    val futureThree: Future[Int] = async {
      await(futureOne) + await(futureTwo)
    }
    futureThree foreach { _ shouldEqual 3 }
  }
}