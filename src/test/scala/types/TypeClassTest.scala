package types

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

trait Joiner[T] {
  def join(list: List[T]): T
}

object Joiner {
  def joinAll[T](list: List[T])(implicit joiner: Joiner[T]): T = joiner.join(list)

  implicit object IntJoiner extends Joiner[Int] {
    def join(list: List[Int]): Int = list.mkString.toInt
  }

  implicit object StringJoiner extends Joiner[String] {
    def join(list: List[String]): String = list.mkString
  }
}

class TypeClassTest extends AnyFunSuite with Matchers {
  test("type class") {
    import Joiner._
    joinAll(List(1, 2, 3)) shouldBe 123
    joinAll(List("Scala ", "is ", "awesome!")) shouldBe "Scala is awesome!"
  }
}