package future

import org.scalatest.{FunSuite, Matchers}

import scala.async.Async._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AsyncTest extends FunSuite with Matchers {
  test("sequential") {
    val future: Future[Int] = async {
      val futureOne: Future[Int] = async { 1 }
      val futureTwo: Future[Int] = async { 2 }
      await(futureOne) + await(futureTwo)
    }
    future foreach { i => i shouldEqual 3 }
  }

  test("parallel") {
    val futureOne: Future[Int] = async { 1 }
    val futureTwo: Future[Int] = async { 2 }
    val futureThree: Future[Int] = async {
      await(futureOne) + await(futureTwo)
    }
    futureThree foreach { i => i shouldEqual 3 }
  }
}