package parallel

import scala.collection.parallel.immutable.ParRange

import org.scalatest.FunSuite

class ParallelTest extends FunSuite {
  test("split recursive sum") {
    def sum(ints: IndexedSeq[Int]): Int = {
      if (ints.size <= 1)
        ints.headOption getOrElse 0
      else {
        val (l, r) = ints.splitAt(ints.length / 2)
        sum(l) + sum(r)
      }
    }
    val range = Range(1, 1000000)
    val total = sum(range)
    assert(total == 1783293664)
  }

  test("parallel sum") {
    val range = new ParRange(Range(1, 1000000))
    val total = range.sum
    assert(total == 1783293664)
  }
}