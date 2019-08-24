package collection

import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable

class IndexedSeqTest extends FunSuite with Matchers {
  test("vector") {
    val vector = Vector(1, 2)
    vector.length shouldEqual 2
    vector(0) shouldEqual 1
    vector(1) shouldEqual 2
    vector.reverse shouldEqual Vector(2, 1)
    vector shouldEqual 1 +: Vector(2)
    vector shouldEqual Vector(1) :+ 2
    vector shouldEqual Vector(1) ++ Vector(2)
    vector shouldEqual Vector(1) ++: Vector(2)
    3 shouldEqual (vector foldRight 0)(_ + _)
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
    queue enqueue 3
    3 shouldEqual queue.last
    queue.dequeue shouldEqual 1
    queue.dequeue shouldEqual 2
    queue.dequeue shouldEqual 3
    queue.isEmpty shouldBe true
  }

  test("stack") {
    val stack = mutable.Stack(2, 1)
    stack push 3
    3 shouldEqual stack.pop
    2 shouldEqual stack.pop
    1 shouldEqual stack.pop
    stack.isEmpty shouldBe true
  }

  test("string builder") {
    val builder = new StringBuilder
    builder.append("a")
    builder.append("b")
    builder.append("c")
    builder.toString shouldEqual "abc"
    builder.result shouldEqual "abc"
    builder.reverse.result shouldEqual "cba"
  }

  test("range") {
    (1 until 10) shouldEqual Range(1, 10, 1)
    (10 until 1 by -1) shouldEqual Range(10, 1, -1)
    (1 to 10) shouldEqual Range.inclusive(1, 10, 1)
  }
}