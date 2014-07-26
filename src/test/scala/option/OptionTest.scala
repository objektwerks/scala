package option

import org.scalatest.FunSuite

import scala.io.Source
import scala.util.control.Exception._
import scala.util.{Success, Try}

class OptionTest extends FunSuite {
  test("option") {
    def greaterThanZero(x: Int) = if (x > 0) Some(x) else None
    assert(greaterThanZero(0) == None)
    assert(greaterThanZero(1) == Some(1))
  }

  test("option try") {
    def toInt(s: String): Option[Int] = {
      try {
        Some(s.toInt)
      } catch {
        case e: Exception => None
      }
    }
    assert(toInt("3") == Some(3))
    assert(toInt("3").get == 3)
    assert(toInt("c") == None)
    assert(toInt("c").getOrElse(0) == 0)
  }

  test("try success failure") {
    def readTextFile(name: String): Try[List[String]] = {
      Try(Source.fromFile(name).getLines().toList)
    }
    assert(readTextFile("/etc/passwd").isSuccess)
    assert(readTextFile("/etc/pass").isFailure)
  }

  test("all catch") {
    def readTextFile(name: String): Option[List[String]] = {
      allCatch.opt(Source.fromFile(name).getLines().toList)
    }
    assert(readTextFile("/etc/passwd").nonEmpty)
    assert(readTextFile("/etc/pass") == None)
  }

  test("either left right") {
    def divide(x: Int, y: Int): Either[String, Int] = {
      try {
        Right(x / y)
      } catch {
        case e: Exception => Left("divide by zero error")
      }
    }
    assert(divide(9, 3) == Right(3))
    assert(divide(9, 0) == Left("divide by zero error"))
  }

  test("try recover") {
    val n = for {
      p <- Try(Integer.parseInt("one")).recover {
        case e => 0
      }
    } yield p
    assert(n == Success(0))
  }
}