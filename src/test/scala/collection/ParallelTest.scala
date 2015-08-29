package collection

import org.scalatest.FunSuite

import scala.collection.parallel.ParSet
import scala.collection.parallel.immutable.{ParMap, ParSeq, ParRange}

class ParallelTest extends FunSuite {
  test("set sum") {
    val set = ParSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21)
    assert(set.sum == 231)
  }

  test("map sum") {
    val map = ParMap(1 -> 1, 2 -> 2, 3 -> 3, 4 -> 4, 5 -> 5, 6 -> 6, 7 -> 7, 8 -> 8)
    assert(map.values.sum == 36)
  }

  test("seq sum") {
    val set = ParSeq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21)
    assert(set.sum == 231)
  }

  test("range sum") {
    val range = ParRange(1, 21, 1, inclusive = true)
    assert(range.sum == 231)
  }
}