package exception

import org.scalatest.FunSuite

import scala.util.Try

class EitherTest extends FunSuite {
  test("either") {
    def divide(x: Int, y: Int): Either[Throwable, Int] = Try(x / y).toEither
    assert(divide(9, 3).isRight)
    assert(divide(9, 0).isLeft)
    assert(divide(9, 3).contains(3))
    assert(divide(9, 3).exists(_ == 3))
    assert(divide(9, 3).getOrElse(-1) == 3)
    assert(divide(9, 3).map(_ * 3).getOrElse(-1) == 9)
    assert(divide(9, 3).map(_ * 3).right.get == 9)
  }
}