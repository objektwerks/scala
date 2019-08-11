package collection

import org.scalatest.{FunSuite, Matchers}

class RangeTest extends FunSuite with Matchers {
  test("range") {
    assert((1 until 10) == Range(1, 10, 1))
    assert((10 until 1 by -1) == Range(10, 1, -1))
    assert((1 to 10) == Range.inclusive(1, 10, 1))
  }
}