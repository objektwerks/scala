package collection

import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

class MutableTest extends FunSuite {
  test("array buffer") {
    var buffer = ArrayBuffer(1, 2, 3)
    assert((buffer += 4) == ArrayBuffer(1, 2, 3, 4))
    assert((buffer -= 4) == ArrayBuffer(1, 2, 3))
  }
}