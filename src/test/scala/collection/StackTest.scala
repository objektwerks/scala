package collection

import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable

class StackTest extends FunSuite with Matchers {
  test("stack") {
    val stack = mutable.Stack(2, 1)
    stack push 3
    assert(3 == stack.pop)
    assert(2 == stack.pop)
    assert(1 == stack.pop)
    assert(stack.isEmpty)
  }
}