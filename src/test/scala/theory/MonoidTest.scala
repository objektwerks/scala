package theory

import org.scalatest.FunSuite

trait Monoid[A] {
  def op(a1: A, a2: A): A
  def zero: A
}

class Adder extends Monoid[Int] {
  def op(a1: Int, a2: Int) = a1 + a2
  def zero: Int = 0
}

class MonoidTest extends FunSuite {
  test("monoid") {
    val adder = new Adder
    assert(adder.op(1, 1) == 2)
    assert(adder.zero == 0)
  }
}