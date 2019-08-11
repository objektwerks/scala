package collection

import org.scalatest.{FunSuite, Matchers}

import scala.collection.parallel.immutable.ParRange
import scala.collection.parallel.{ParMap, ParSeq, ParSet}

class ParallelTest extends FunSuite with Matchers {
  test("par set") {
    val set = ParSet(1 to 1000000:_*)
    assert(set.sum == 1784293664)
  }

  test("par map") {
    val m = for (i <- 1 to 1000000) yield (i , i)
    val map = ParMap(m:_*)
    assert(map.values.sum == 1784293664)
  }

  test("par seq") {
    val seq = ParSeq(1 to 1000000:_*)
    assert(seq.sum == 1784293664)
  }

  test("par range") {
    val range = ParRange(1, 1000000, 1, inclusive = true)
    assert(range.sum == 1784293664)
  }
}