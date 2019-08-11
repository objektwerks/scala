package collection

import org.scalatest.{FunSuite, Matchers}

class VectorTest extends FunSuite with Matchers{
  test("vector") {
    val vector = Vector(1, 2)
    assert(vector.length == 2 && vector(0) == 1 && vector(1) == 2)
    assert(vector.reverse === Vector(2, 1))
    assert(vector === 1 +: Vector(2))
    assert(vector === Vector(1) :+ 2)
    assert(vector === Vector(1) ++ Vector(2))
    assert(vector === Vector(1) ++: Vector(2))
    assert(3 == (vector foldRight 0)(_ + _))
  }
}