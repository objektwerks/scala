package option

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.util.Try

class OptionTest extends AnyFunSuite with Matchers {
  def greaterThanZero(x: Int): Option[Int] = if (x > 0) Some(x) else None

  test("option") {
    greaterThanZero(1).get shouldEqual 1
    greaterThanZero(0).getOrElse(1) shouldEqual 1
    greaterThanZero(0) orElse Some(-1) contains -1 shouldBe true

    greaterThanZero(0).isEmpty shouldBe true
    greaterThanZero(1).nonEmpty shouldBe true
    greaterThanZero(1).isDefined shouldBe true

    greaterThanZero(1) collect { case n: Int => n * 3 } contains 3 shouldBe true

    greaterThanZero(1).contains(1) shouldBe true
    greaterThanZero(1).count(_ > 0) shouldEqual 1

    greaterThanZero(1).exists(_ > 0) shouldBe true
    greaterThanZero(1).filter(_ > 0) contains 1 shouldBe true

    greaterThanZero(1).forall(_ > 0) shouldBe true
    greaterThanZero(3) foreach { v => v shouldEqual 3 }

    greaterThanZero(1).fold(1)(_ * 3) shouldEqual 3
  }

  test("match") {
    val x = greaterThanZero(1) match {
      case Some(n) => n * 3
      case None => -1
    }
    x shouldEqual 3
  }

  test("map") {
    greaterThanZero(1) map(_ * 3) getOrElse(-1) shouldEqual 3
  }

  test("flatmap") {
    def toInt(s: String): Option[Int] = Try(s.toInt).toOption

    val strings = List("1", "2", "3", "four")
    strings.flatMap(toInt) shouldEqual List(1, 2, 3)
    strings.flatMap(toInt).sum shouldEqual 6

    def sum(x: Option[Int], y: Option[Int]): Option[Int] = x.flatMap(i => y.map(j => i + j))
    
    sum(toInt("1"), toInt("2")).contains(3) shouldBe true
    sum(toInt("1"), toInt("zspace")).isEmpty shouldBe true
  }
}