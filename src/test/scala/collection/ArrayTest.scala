package collection

import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable

class ArrayTest extends FunSuite with Matchers {
  test("array") {
    val array = Array(1, 2)
    assert(array.length == 2 && array(0) == 1 && array(1) == 2)
    assert(array.reverse === Array(2, 1))
    assert(array === 1 +: Array(2))
    assert(array === Array(1) :+ 2)
    assert(array === Array(1) ++ Array(2))
    assert(array === Array(1) ++: Array(2))
    assert(3 == (array foldRight 0)(_ + _))
  }

  test("array buffer") {
    val buffer = mutable.ArrayBuffer(1, 2)
    assert((buffer += 3) == mutable.ArrayBuffer(1, 2, 3))
    assert((buffer -= 3) == mutable.ArrayBuffer(1, 2))
    assert((buffer -= 2) == mutable.ArrayBuffer(1))
    assert((buffer -= 1) == mutable.ArrayBuffer())
  }
}