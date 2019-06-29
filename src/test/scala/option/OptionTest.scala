package option

import org.scalatest.FunSuite

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

class OptionTest extends FunSuite {
  test("option") {
    def greaterThanZero(x: Int): Option[Int] = if (x > 0) Some(x) else None
    assert(greaterThanZero(1).get == 1)
    assert(greaterThanZero(0).getOrElse(1) == 1)
    assert(greaterThanZero(0) orElse Some(-1) contains -1)
    assert(greaterThanZero(0).isEmpty)
    assert(greaterThanZero(1).nonEmpty)
    assert(greaterThanZero(1).isDefined)
    assert(greaterThanZero(1) collect { case n: Int => n * 3 } contains 3)
    assert(greaterThanZero(1).contains(1))
    assert(greaterThanZero(1).count(_ > 0) == 1)
    assert(greaterThanZero(1).exists(_ > 0))
    assert(greaterThanZero(1).filter(_ > 0) contains 1)
    assert(greaterThanZero(1).filterNot(_ > 0) isEmpty)
    assert(greaterThanZero(1).forall(_ > 0))
    val x = greaterThanZero(1) match {
      case Some(n) => n * 3
      case None => -1
    }
    assert(x == 3)
    val y = greaterThanZero(1) map(_ * 3) getOrElse(-1)
    assert(y == 3)
    val z = greaterThanZero(1).fold(1)(_ * 3)
    assert(z == 3)
    greaterThanZero(3) foreach { v => assert(v == 3) }
  }

  test("flatten") {
    assert(List(Some(1), Some(2), Some(3)).flatten == List(1, 2, 3))
    assert(List(Some(1), None, Some(3)).flatten.sum == 4)
    assert(Some(List(Some(1), Some(2), Some(3))).getOrElse(Nil).flatten == List(1, 2, 3))
    assert(Some(List(Some(1), Some(2), Some(3))).getOrElse(Nil).flatten.sum == 6)
  }

  test("flatmap") {
    def toInt(s: String): Option[Int] = Try(s.toInt).toOption
    val strings = List("1", "2", "3", "four")
    assert(strings.flatMap(toInt) == List(1, 2, 3))
    assert(strings.flatMap(toInt).sum == 6)

    def sum(x: Option[Int], y: Option[Int]): Option[Int] = x.flatMap(i => y.map(j => i + j))
    assert(sum(toInt("1"), toInt("2")).contains(3))
    assert(sum(toInt("1"), toInt("zspace")).isEmpty)
  }

  test("for comprehension") {
    val option = Option(List(Some(1), Some(2), Some(3)))
    val result = for {
      numbers <- option
    } yield numbers.flatten.sum
    assert(result.getOrElse(-1) == 6)
  }

  test("future") {
    implicit val ec = ExecutionContext.global
    val square = (x: Int) => x * x

    val option = Option(List(Some(1), Some(2), Some(3)))
    val future = Future { option }
    future onComplete {
      case Success(numbers) => assert(square(numbers.getOrElse(Nil).flatten.sum) == 36)
      case Failure(failure) => throw failure
    }
  }
}