package collection

import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable

class IndexedSeqTest extends FunSuite with Matchers {
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

  test("queue") {
    val queue = mutable.Queue(1, 2)
    queue enqueue  3
    assert(3 == queue.last)
    assert(queue.dequeue() == 1)
    assert(queue.dequeue() == 2)
    assert(queue.dequeue() == 3)
    assert(queue.isEmpty)
  }

  test("stack") {
    val stack = mutable.Stack(2, 1)
    stack push 3
    assert(3 == stack.pop)
    assert(2 == stack.pop)
    assert(1 == stack.pop)
    assert(stack.isEmpty)
  }

  test("string builder") {
    val builder = new StringBuilder
    builder.append("a")
    builder.append("b")
    builder.append("c")
    assert(builder.toString() == "abc")
    assert(builder.result() == "abc")
    assert(builder.reverse.result() == "cba")
  }

  test("range") {
    assert((1 until 10) == Range(1, 10, 1))
    assert((10 until 1 by -1) == Range(10, 1, -1))
    assert((1 to 10) == Range.inclusive(1, 10, 1))
  }
}