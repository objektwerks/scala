package collection

import org.scalatest.FunSuite

import scala.collection.mutable._

class MutableTest extends FunSuite {
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
    var map = Map[Int, Int](1 -> 1, 2 -> 2, 3 -> 3)
    assert((map += 4 -> 4) == Map[Int, Int](1 -> 1, 2 -> 2, 3 -> 3, 4 -> 4))
    assert((map -= 4) == Map[Int, Int](1 -> 1, 2 -> 2, 3 -> 3))
  }
}