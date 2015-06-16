package option

import org.scalatest.FunSuite

import scala.io.Source
import scala.util.control.Exception._
import scala.util.{Success, Try}
import scalafx.beans.value

class OptionTest extends FunSuite {
  test("option") {
    def greaterThanZero(x: Int): Option[Int] = if (x > 0) Some(x) else None
    assert(greaterThanZero(0).isEmpty)
    assert(greaterThanZero(1).contains(1))
    val opt = greaterThanZero(1)
    opt match {
      case Some(i) => i
      case None => throw new IllegalArgumentException("option equals none")
    }
    assert(opt.get == 1)
   }

  test("option isDefined & isEmpty") {
    val some = Some("value")
    assert(some.isDefined)
    val none = None
    assert(none.isEmpty)
  }

  test("option get & getOrElse") {
    val some = Some(1)
    assert(some.get == 1)
    val none = None
    assert(none.getOrElse(3) == 3)
  }

  test("option map & flatmap") {
    def toInt(s: String): Option[Int] = Some(Integer.parseInt(s.trim))
    val strings = Seq("1", "2", "3")
    assert(strings.map(toInt) == List(Some(1), Some(2), Some(3)))
    assert(strings.flatMap(toInt).sum == 6)
  }

  test("option exists") {
    val number = Some(1)
    assert(number.exists(n => n > 0 ))
  }

  test("option for") {
    val left = Some(1)
    val right = Some(1)
    val result = for {
      l <- left
      r <- right
    } yield l + r
    assert(result.getOrElse(-1) == 2)
  }

  test("option orElse") {
    val resource: Option[String] = None
    val defaultResource: Option[String] = Some("default")
    val locatedResource: Option[String] = resource orElse defaultResource
    assert(locatedResource == defaultResource)
  }

  test("option collect") {
    val value = Some(1)
    value collect { case v:Int => assert(v == 1) }
  }

  test("option foreach") {
    val values = List(Some(1), Some(2), Some(3))
    values foreach { case Some(v) => assert(v < 4) }
  }

  test("option try") {
    def parseInt(s: String): Option[Int] = Some(Integer.parseInt(s.trim))
    assert(Try(parseInt("a")).isFailure)
  }

  test("either") {
    def divide(x: Int, y: Int): Either[String, Int] = try {
      Right(x / y)
    } catch {
      case t: Throwable => Left("divide by zero error")
    }
    assert(divide(9, 3) == Right(3))
    assert(divide(9, 0) == Left("divide by zero error"))
  }

  test("try") {
    def readTextFile(name: String): Try[List[String]] = {
      Try(Source.fromFile(name).getLines.toList)
    }
    assert(readTextFile("build.sbt").isSuccess)
    assert(readTextFile("sbt.sbt").isFailure)
  }

  test("try recover") {
    val n = for {
      i <- Try(Integer.parseInt("one")).recover { case e => 0 }
    } yield i
    assert(n == Success(0))
  }

  test("all catch") {
    def readTextFile(name: String): Option[List[String]] = {
      allCatch.opt(Source.fromFile(name).getLines.toList)
    }
    assert(readTextFile("build.sbt").nonEmpty)
    assert(readTextFile("sbt.sbt").isEmpty)
  }
}