package exception

import org.scalatest.FunSuite

import scala.util.Try

class EitherTest extends FunSuite {
  test("either") {
    def divide(x: Int, y: Int): Either[Throwable, Int] = Try(x / y).toEither
    assert(divide(9, 3).isRight)
    assert(divide(9, 0).isLeft)
  }
}