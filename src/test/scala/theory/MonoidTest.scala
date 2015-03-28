package theory

import org.scalatest.FunSuite

class MonoidTest extends FunSuite {
  test("monoid") {
    assert(Adder.op(1, 1) == 2)
    assert(Adder.zero == 0)
    assert(Adder.fold(List(1, 2, 3)) == 6)
    assert(Adder.isValid(1, 2, 3))
  }
}