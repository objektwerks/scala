package exception

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.util.Try

class EitherTest extends AnyFunSuite with Matchers {
  def divide(x: Int, y: Int): Either[Throwable, Int] = Try(x / y).toEither

  test("either") {
    divide(9, 3).isRight shouldBe true
    divide(9, 0).isLeft shouldBe true
    divide(9, 3).contains(3) shouldBe true
    divide(9, 3).exists(_ == 3) shouldBe true
    divide(9, 3).getOrElse(-1) shouldEqual 3
    divide(9, 3).map(_ * 3).getOrElse(-1) shouldEqual 9
    divide(9, 3).map(_ * 3).filterOrElse(_ == 9, -1).getOrElse(-1) shouldEqual 9
    divide(3, 0) match {
      case Right(_) => fail("Should be divide by zero error.")
      case Left(error) => error.isInstanceOf[Throwable] shouldBe true
    }
  }
}