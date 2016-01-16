package option

import org.scalatest.FunSuite

import scala.io.Source
import scala.util.control.Exception._
import scala.util.{Success, Try}

class OptionTest extends FunSuite {
  test("option") {
    def greaterThanZero(x: Int): Option[Int] = if (x > 0) Some(x) else None
    assert(greaterThanZero(1).get == 1)
    assert(greaterThanZero(0).getOrElse(1) == 1)
    assert(greaterThanZero(0).isEmpty)
    assert(greaterThanZero(1).nonEmpty)
    assert(greaterThanZero(1).isDefined)
    assert(greaterThanZero(1) collect { case n: Int => n * 3 } contains 3)
    assert(greaterThanZero(1).contains(1))
    assert(greaterThanZero(1).count(_ > 0) == 1)
    assert(greaterThanZero(1).exists(_ > 0))
    val x = greaterThanZero(1) match {
      case Some(n) => n
      case None => -1
    }
    assert(x == 1)
    val y = greaterThanZero(1) map(_ * 3) getOrElse(-1)
    assert(y == 3)
    val z = greaterThanZero(1).fold(-1)(_ * 3)
    assert(z == 3)
  }

  test("option orElse") {
    val resource: Option[String] = None
    val defaultResource: Option[String] = Some("default")
    val locatedResource: Option[String] = resource orElse defaultResource
    assert(locatedResource == defaultResource)
  }

  test("option foreach") {
    val values = List(Some(1), Some(2), Some(3))
    values foreach { case Some(v) => assert(v < 4) }
  }

  test("option map & flatmap") {
    def toInt(s: String): Option[Int] = if(s matches "\\d+") Some(s.toInt) else None
    val strings = Seq("1", "2", "3")
    assert(strings.map(toInt) == List(Some(1), Some(2), Some(3)))
    assert(strings.flatMap(toInt) == List(1, 2, 3))
    assert(strings.flatMap(toInt).sum == 6)

    def sum(x: Option[Int], y: Option[Int]): Option[Int] = x.flatMap(a => y.map(b => a + b))
    assert(sum(toInt("1"), toInt("2")).contains(3))
    assert(sum(toInt("1"), toInt("z")).isEmpty)
  }

  test("option for") {
    val left = Some(1)
    val right = Some(2)
    val result = for {
      l <- left
      r <- right
    } yield l + r
    assert(result.getOrElse(-1) == 3)
  }
}