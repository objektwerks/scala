package parallel

import org.scalatest.FunSuite

class ParallelTest extends FunSuite {
  test("split sum") {
    def sum(ints: IndexedSeq[Int]): Int = {
      if (ints.size <= 1)
        ints.headOption getOrElse 0
      else {
        val (l, r) = ints.splitAt(ints.length / 2)
        sum(l) + sum(r)
      }
    }
    val total = sum(Range.apply(1, 1000000))
    assert(total == 1783293664)
  }
}