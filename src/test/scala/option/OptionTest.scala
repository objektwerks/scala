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
    opt match {
      case Some(i) => assert(i > 0)
      case None => throw new IllegalArgumentException("option test failed")
    }
    opt map(i => assert(i > 0)) getOrElse(throw new IllegalArgumentException("option test failed"))
    opt.fold (throw new IllegalArgumentException("option test faild")) (i => assert(i > 0))
   }

  test("option flatmap") {
    def toInt(s: String): Option[Int] = Some(Integer.parseInt(s.trim))
    val strings = Seq("1", "2", "3")
    assert(strings.flatMap(toInt) == List(1, 2, 3))
    assert(strings.flatMap(toInt).sum == 6)
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
    def divide(x: Int, y: Int): Either[String, Int] = try {
      Right(x / y)
    } catch {
      case t: Throwable => Left("divide by zero error")
    }
    assert(divide(9, 3) == Right(3))
    assert(divide(9, 0) == Left("divide by zero error"))
  }

  test("try recover") {
    val n = for {
      i <- Try(Integer.parseInt("one")).recover { case e => 0 }
    } yield i
    assert(n == Success(0))
  }
}