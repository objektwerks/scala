package exception

import org.scalatest.{FunSuite, Matchers}

import scala.io.Source
import scala.util.control.Exception._
import scala.util.control.NonFatal
import scala.util.{Success, Try, Using}

class TryTest extends FunSuite with Matchers {
  def divide(x: String, y: String): Try[Int] = {
    for {
      x <- Try(x.toInt)
      y <- Try(y.toInt)
    } yield x / y
  }

  def fileToLines(file: String): Try[Seq[String]] = Using(Source.fromFile(file)) { source => source.getLines.toSeq }

  def parseInt(s: String): Option[Int] = Try(s.toInt).toOption

  test("try catch handler") {
    val handler: PartialFunction[Throwable, Unit] = {
      case NonFatal(error) => error.getMessage.nonEmpty shouldBe true; ()
    }
    try "abc".toInt catch handler
  }

  test("try") {
    divide("9", "3").isSuccess shouldBe true
    divide("9", "3").toOption.contains(3) shouldBe true
    divide("9", "3").get shouldEqual 3
    divide("a", "b").isFailure shouldBe true
    divide("a", "b").toOption.isEmpty shouldBe true
    divide("a", "b").getOrElse(-1) shouldEqual -1
  }

  test("try option") {
    parseInt("a").isEmpty shouldBe true
    parseInt("1").isDefined shouldBe true
  }

  test("try using") {
    fileToLines("build.sbt").isSuccess shouldBe true
    fileToLines("sbt.sbt").isFailure shouldBe true
  }

  test("try recover") {
    val i = for {
      i <- Try("one".toInt).recover { case _ => 0 }
    } yield i
    i shouldEqual Success(0)
  }

  test("all catch") {
    allCatch.opt("1".toInt).nonEmpty shouldBe true
    allCatch.opt("one".toInt).isEmpty shouldBe true
  }
}