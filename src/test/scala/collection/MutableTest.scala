package collection

import org.scalatest.FunSuite

import scala.collection.mutable

class MutableTest extends FunSuite {
  test("fifo queue") {
    val queue = mutable.Queue(1, 2, 3)
    queue enqueue  4
    assert(4 == queue.last)
    assert(queue.dequeue() == 1)
  }

  test("lifo stack") {
    val stack = mutable.Stack(3, 2, 1)
    stack push 4
    assert(4 == stack.pop)
    assert(3 == stack.pop)
  }

  test("array buffer") {
    var buffer = mutable.ArrayBuffer(1, 2, 3)
    assert((buffer += 4) == mutable.ArrayBuffer(1, 2, 3, 4))
    assert((buffer -= 4) == mutable.ArrayBuffer(1, 2, 3))
  }

  test("list buffer") {
    var buffer = mutable.ListBuffer(1, 2, 3)
    assert((buffer += 4) == mutable.ListBuffer(1, 2, 3, 4))
    assert((buffer -= 4) == mutable.ListBuffer(1, 2, 3))
  }

  test("list map") {
    var map = mutable.Map(1 -> 1, 2 -> 2, 3 -> 3)
    assert((map += 4 -> 4) == Map(1 -> 1, 2 -> 2, 3 -> 3, 4 -> 4))
    assert((map -= 4) == Map(1 -> 1, 2 -> 2, 3 -> 3))
  }
}