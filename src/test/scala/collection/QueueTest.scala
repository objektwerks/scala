package collection

import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable

class QueueTest extends FunSuite with Matchers {
  test("queue") {
    val queue = mutable.Queue(1, 2)
    queue enqueue  3
    assert(3 == queue.last)
    assert(queue.dequeue() == 1)
    assert(queue.dequeue() == 2)
    assert(queue.dequeue() == 3)
    assert(queue.isEmpty)
  }
}