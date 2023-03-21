package objektwerks.option

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

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
    def toInt(s: String): Option[Int] = s.toIntOption

    val strings = List("1", "2", "3", "four")
    strings.map(toInt) shouldBe List(Some(1), Some(2), Some(3), None)
    strings.map(toInt).collect { case Some(i) => i } shouldBe List(1, 2, 3)
    strings.map(toInt).flatten shouldBe List(1, 2, 3)
    strings.flatMap(toInt) shouldBe List(1, 2, 3)
    strings.flatMap(toInt).sum shouldBe 6
  }
}