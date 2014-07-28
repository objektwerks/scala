package types

import org.scalatest.FunSuite

class TypesTest extends FunSuite {
  test("generic function") {
    def getMiddle[A](a: Array[A]): A = a(a.length / 2)
    assert(getMiddle(Array("a", "b", "c")) == "b")
  }
}