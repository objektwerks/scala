package exception

import org.scalatest.FunSuite

import scala.io.Source
import scala.util.control.Exception._
import scala.util.control.NonFatal
import scala.util.{Try, Success}

class ExceptionTest extends FunSuite {
  test("try catch handler") {
    val handler: PartialFunction[Throwable, Unit] = {
      case NonFatal(error) => assert(error.getMessage.nonEmpty)
    }
    try "abc".toInt catch handler
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
    def divide(x: String, y: String): Try[Int] = {
      for {
        x <- Try(x.toInt)
        y <- Try(y.toInt)
      } yield x / y
    }
    assert(divide("9", "3").isSuccess)
    assert(divide("9", "3").get == 3)
    assert(divide("a", "b").isFailure)
  }

  test("try option") {
    def parseInt(s: String): Option[Int] = Some(Integer.parseInt(s.trim))
    assert(Try(parseInt("a")).isFailure)
    assert(Try(parseInt("1")).isSuccess)
  }

  test("try source") {
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