package collection

import org.scalatest.FunSuite

import scala.collection.mutable.{ListBuffer, ArrayBuffer}

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
}