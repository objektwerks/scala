package collection

import org.scalatest.FunSuite

import scala.collection.parallel.ParSet
import scala.collection.parallel.immutable.{ParMap, ParSeq, ParRange}

class ParallelTest extends FunSuite {
  test("set") {
    val set = ParSet(1 to 21:_*)
    assert(set.sum == 231)
  }

  test("map") {
    val m = for (i <- 1 to 21) yield (i , i)
    val map = ParMap(m:_*)
    assert(map.values.sum == 231)
  }

  test("seq") {
    val seq = ParSeq(1 to 21:_*)
    assert(seq.sum == 231)
  }

  test("range") {
    val range = ParRange(1, 21, 1, inclusive = true)
    assert(range.sum == 231)
  }
}