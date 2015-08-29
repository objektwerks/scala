package collection

import org.scalatest.FunSuite

import scala.collection.parallel.immutable.ParRange

class ParallelTest extends FunSuite {
  test("parallel sum") {
    val range = new ParRange(Range(1, 1000000))
    assert(range.sum == 1783293664)
  }
}