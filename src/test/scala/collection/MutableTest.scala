package collection

import org.scalatest.FunSuite

import scala.collection.mutable
import scala.collection.mutable.{ListBuffer, ArrayBuffer}

class MutableTest extends FunSuite {
  test("queue") {
    val queue = mutable.Queue(1, 2, 3)
    queue enqueue  4
    assert(4 == queue.last)
  }

  test("stack") {
    val stack = mutable.Stack(1, 2, 3)
    stack push 4
    assert(4 == stack.pop)
  }

  test("array buffer") {
    var buffer = ArrayBuffer(1, 2, 3)
    assert((buffer += 4) == ArrayBuffer(1, 2, 3, 4))
    assert((buffer -= 4) == ArrayBuffer(1, 2, 3))
  }

  test("list buffer") {
    var buffer = ListBuffer(1, 2, 3)
    assert((buffer += 4) == ListBuffer(1, 2, 3, 4))
    assert((buffer -= 4) == ListBuffer(1, 2, 3))
  }

  test("list map") {
    var map = mutable.Map(1 -> 1, 2 -> 2, 3 -> 3)
    assert((map += 4 -> 4) == Map(1 -> 1, 2 -> 2, 3 -> 3, 4 -> 4))
    assert((map -= 4) == Map(1 -> 1, 2 -> 2, 3 -> 3))
  }
}