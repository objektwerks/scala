package collection

import org.scalatest.FunSuite

import scala.collection.mutable

class MutableTest extends FunSuite {
  test("fifo queue") {
    val queue = mutable.Queue(1, 2)
    queue enqueue  3
    assert(3 == queue.last)
    assert(queue.dequeue() == 1)
    assert(queue.dequeue() == 2)
    assert(queue.dequeue() == 3)
    assert(queue.isEmpty)
  }

  test("lifo stack") {
    val stack = mutable.Stack(2, 1)
    stack push 3
    assert(3 == stack.pop)
    assert(2 == stack.pop)
    assert(1 == stack.pop)
    assert(stack.isEmpty)
  }

  test("array buffer") {
    var buffer = mutable.ArrayBuffer(1, 2)
    assert((buffer += 3) == mutable.ArrayBuffer(1, 2, 3))
    assert((buffer -= 3) == mutable.ArrayBuffer(1, 2))
    assert((buffer -= 2) == mutable.ArrayBuffer(1))
    assert((buffer -= 1) == mutable.ArrayBuffer())
  }

  test("list buffer") {
    var buffer = mutable.ListBuffer(1, 2)
    assert((buffer += 3) == mutable.ListBuffer(1, 2, 3))
    assert((buffer -= 3) == mutable.ListBuffer(1, 2))
    assert((buffer -= 2) == mutable.ListBuffer(1))
    assert((buffer -= 1) == mutable.ListBuffer())
  }

  test("mutable map") {
    var map = mutable.Map(1 -> 1, 2 -> 2)
    assert((map += 3 -> 3) == Map(1 -> 1, 2 -> 2, 3 -> 3))
    assert((map -= 3) == Map(1 -> 1, 2 -> 2))
    assert((map -= 2) == Map(1 -> 1))
    assert((map -= 1) == Map())
  }
}