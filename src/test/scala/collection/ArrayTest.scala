package collection

import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable

class ArrayTest extends FunSuite with Matchers {
  test("array") {
    val array = Array(1, 2)
    1 +: Array(2) shouldEqual array
    Array(1) :+ 2 shouldEqual array
    Array(1) ++ Array(2) shouldEqual array
    Array(1) ++: Array(2) shouldEqual array
  }

  test("array buffer") {
    val buffer = mutable.ArrayBuffer(1, 2)
    (buffer += 3) shouldEqual mutable.ArrayBuffer(1, 2, 3)
    (buffer -= 3) shouldEqual mutable.ArrayBuffer(1, 2)
  }

  test("array deque") {
    val deque = mutable.ArrayDeque(1, 2)
    (deque += 3) shouldEqual mutable.ArrayDeque(1, 2, 3)
    (deque -= 3) shouldEqual mutable.ArrayDeque(1, 2)
  }
}