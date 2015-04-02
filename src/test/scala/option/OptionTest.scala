package option

import scala.io.Source
import scala.util.control.Exception._
import scala.util.{Success, Try}

import org.scalatest.FunSuite

class OptionTest extends FunSuite {
  test("option") {
    def greaterThanZero(x: Int): Option[Int] = if (x > 0) Some(x) else None
    assert(greaterThanZero(0) == None)
    assert(greaterThanZero(1) == Some(1))
    val opt = greaterThanZero(1)
    val value: Int = opt match {
      case Some(i) => i
      case None => throw new IllegalArgumentException("option equals none")
    }
    assert(value == 1)
   }

  test("option map > flatmap ") {
    def toInt(s: String): Option[Int] = Some(Integer.parseInt(s.trim))
    val strings = Seq("1", "2", "3")
    assert(strings.map(toInt) == List(Some(1), Some(2), Some(3)))
    assert(strings.flatMap(toInt).sum == 6)
  }

  test("all catch") {
    def readTextFile(name: String): Option[List[String]] = {
      allCatch.opt(Source.fromFile(name).getLines().toList)
    }
    assert(readTextFile("build.sbt").nonEmpty)
    assert(readTextFile("sbt.sbt") == None)
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
      Try(Source.fromFile(name).getLines().toList)
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
}