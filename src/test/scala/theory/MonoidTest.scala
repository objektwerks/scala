package theory

import org.scalatest.FunSuite

/** See Functional Programming in Scala, Part 3, Chapter 10 */
trait Monoid[A] {
  def op(a1: A, a2: A): A
  def zero: A
}

object Monoid {
  def add = new Monoid[Int] {
    def op(a1: Int, a2: Int): Int = a1 + a2
    def zero: Int = 0
  }

  def multiply = new Monoid[Int] {
    def op(a1: Int, a2: Int): Int = a1 * a2
    def zero: Int = 0
  }

  def concat = new Monoid[String] {
    def op(a1: String, a2: String): String = a1 + a2
    def zero: String = ""
  }
}

class MonoidTest extends FunSuite {
  test("monoid") {
    assert(Monoid.add.op(1, 1) == 2)
    assert(Monoid.add.zero == 0)
    assert(Monoid.multiply.op(1, 1) == 1)
    assert(Monoid.multiply.zero == 0)
    assert(Monoid.concat.op("a", "b") == "ab")
    assert(Monoid.concat.zero == "")
  }
}