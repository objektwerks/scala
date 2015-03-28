package theory

import org.scalatest.FunSuite

/** See Functional Programming in Scala, Part 3, Chapter 10.
  *
  * A monoid is a (1) type together with a binary operation (op)
  * over that type, satisfying (2) associativity and having an
  * (3) identity element (zero).
  * */
trait Monoid[A] {
  def op(a1: A, a2: A): A
  def zero: A
}

class Adder extends Monoid[Int] {
  def op(x: Int, y: Int): Int = x + y
  def zero: Int = 0
  def isValid(x: Int, y: Int, z: Int): Boolean = {
    val associative = op(op(x, y), z) == op(x, op(y, z))
    val identity = op(zero, x) == x
    associative && identity
  }
}

class MonoidTest extends FunSuite {
  val adder = new Adder

  test("monoid") {
    assert(adder.op(1, 1) == 2)
    assert(adder.zero == 0)
    assert(adder.isValid(1, 2, 3))
  }

  test("fold") {
    val nums = List(1, 2, 3)
    assert(6 == nums.foldLeft(adder.zero)(adder.op))
    assert(6 == nums.foldRight(adder.zero)(adder.op))
    assert(6 == nums.fold(adder.zero)(adder.op))
  }
}