package exception

import org.scalatest.FunSuite

import scala.io.Source
import scala.util.control.Exception._
import scala.util.control.NonFatal
import scala.util.{Success, Try, Using}

class TryTest extends FunSuite {
  def lines(file: String): Try[Seq[String]] = Using(Source.fromFile(file)) { source => source.getLines.toSeq }

  test("try catch handler") {
    val handler: PartialFunction[Throwable, Unit] = {
      case NonFatal(error) => assert(error.getMessage.nonEmpty); ()
    }
    try "abc".toInt catch handler
  }

  test("try") {
    def divide(x: String, y: String): Try[Int] = {
      for {
        x <- Try(x.toInt)
        y <- Try(y.toInt)
      } yield x / y
    }
    assert(divide("9", "3").isSuccess)
    assert(divide("9", "3").toOption.contains(3))
    assert(divide("9", "3").get == 3)
    assert(divide("a", "b").isFailure)
    assert(divide("a", "b").toOption.isEmpty)
    assert(divide("a", "b").getOrElse(-1) == -1)
  }

  test("try option") {
    def parseInt(s: String): Option[Int] = Try(s.toInt).toOption
    assert(parseInt("a").isEmpty)
    assert(parseInt("1").isDefined)
  }

  test("try source") {
    assert(lines("build.sbt").isSuccess)
    assert(lines("sbt.sbt").isFailure)
  }

  test("try recover") {
    val n = for {
      i <- Try(Integer.parseInt("one")).recover { case _ => 0 }
    } yield i
    assert(n == Success(0))
  }

  test("all catch") {
    assert(allCatch.opt( "1".toInt ).nonEmpty)
    assert(allCatch.opt( "one".toInt ).isEmpty)
  }
}