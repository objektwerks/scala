package objektwerks.collection

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.collection.parallel.immutable.ParRange
import scala.collection.parallel.{ParMap, ParSeq, ParSet}

class ParallelTest extends AnyFunSuite with Matchers {
  test("par set") {
    val set = ParSet(1 to 1000000:_*)
    set.sum shouldEqual 1784293664
  }

  test("par map") {
    val m = for (i <- 1 to 1000000) yield (i , i)
    val map = ParMap(m:_*)
    map.values.sum shouldEqual 1784293664
  }

  test("par seq") {
    val seq = ParSeq(1 to 1000000:_*)
    seq.sum shouldEqual 1784293664
  }

  test("par range") {
    val range = ParRange(1, 1000000, 1, inclusive = true)
    range.sum shouldEqual 1784293664
  }
}