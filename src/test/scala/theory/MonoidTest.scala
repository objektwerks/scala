package theory

import org.scalatest.FunSuite

trait Monoid[A] {
  def op(a1: A, a2: A): A

  def zero: A
}

object Adder extends Monoid[Int] {
  override def op(x: Int, y: Int): Int = x + y

  override def zero: Int = 0

  def fold(xs: List[Int]): Int = xs.fold(zero)(op)

  def isValid(x: Int, y: Int, z: Int): Boolean = {
    val associative = op(op(x, y), z) == op(x, op(y, z))
    val identity = op(zero, x) == x
    associative && identity
  }
}

class MonoidTest extends FunSuite {
  test("monoid") {
    assert(Adder.op(1, 1) == 2)
    assert(Adder.zero == 0)
    assert(Adder.fold(List(1, 2, 3)) == 6)
    assert(Adder.isValid(1, 2, 3))
  }
}