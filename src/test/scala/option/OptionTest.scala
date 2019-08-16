package option

import org.scalatest.FunSuite

import scala.util.Try

class OptionTest extends FunSuite {
  def greaterThanZero(x: Int): Option[Int] = if (x > 0) Some(x) else None

  test("option") {
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
    assert(greaterThanZero(1).forall(_ > 0))
    greaterThanZero(3) foreach { v => assert(v == 3) }

    assert(greaterThanZero(1).fold(1)(_ * 3) == 3)

  }

  test("match") {
    val x = greaterThanZero(1) match {
      case Some(n) => n * 3
      case None => -1
    }
    assert(x == 3)
  }

  test("map") {
    val i = greaterThanZero(1) map(_ * 3) getOrElse(-1)
    assert(i == 3)
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
}