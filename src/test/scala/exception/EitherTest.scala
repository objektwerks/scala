package exception

import org.scalatest.FunSuite

import scala.util.Try

class EitherTest extends FunSuite {
  def divide(x: Int, y: Int): Either[Throwable, Int] = Try(x / y).toEither

  test("either") {
    assert(divide(9, 3).isRight)
    assert(divide(9, 0).isLeft)
    assert(divide(9, 3).contains(3))
    assert(divide(9, 3).exists(_ == 3))
    assert(divide(9, 3).getOrElse(-1) == 3)
    assert(divide(9, 3).map(_ * 3).getOrElse(-1) == 9)
    assert(divide(9, 3).map(_ * 3).filterOrElse(_ == 9, -1).getOrElse(-1) == 9)
    divide(3, 0) match {
      case Right(_) => throw new Exception("Should be divide by zero error.")
      case Left(error) => assert(error.isInstanceOf[Throwable])
    }
  }
}