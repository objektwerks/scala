package objektwerks.exception

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.io.{Codec, Source}
import scala.util.control.Exception._
import scala.util.control.NonFatal
import scala.util.{Success, Try, Using}

class TryTest extends AnyFunSuite with Matchers {
  def divide(x: String, y: String): Option[Int] = {
    for {
      x <- x.toIntOption
      y <- y.toIntOption
    } yield x / y
  }

  def parseInt(s: String): Option[Int] = s.toIntOption

  def fileToLines(file: String): Try[Seq[String]] = Using( Source.fromFile(file, Codec.UTF8.name) ) { source => source.getLines().toSeq }

  test("option") {
    divide("9", "3").nonEmpty shouldBe true
    divide("9", "3").contains(3) shouldBe true
    divide("9", "3").get shouldEqual 3
    divide("a", "b").isEmpty shouldBe true
    divide("a", "b").isEmpty shouldBe true
    divide("a", "b").getOrElse(-1) shouldEqual -1

    parseInt("a").isEmpty shouldBe true
    parseInt("1").isDefined shouldBe true
  }

  test("option all catch") {
    allCatch.opt("1".toInt).nonEmpty shouldBe true
    allCatch.opt("one".toInt).isEmpty shouldBe true
  }

  test("try") {
    fileToLines("build.sbt").isSuccess shouldBe true
    fileToLines("sbt.sbt").isFailure shouldBe true
  }

  test("try recover") {
    Try("one".toInt).recover { case _ => 0 } shouldEqual Success(0)
  }

  test("try catch handler") {
    val handler: PartialFunction[Throwable, Unit] = {
      case NonFatal(error) => error.getMessage.nonEmpty shouldBe true; ()
    }
    try "abc".toInt catch handler
  }
}