package z

import math.Fibonacci
import org.scalatest.FunSuite

import scalaz._

class MemoTest extends FunSuite {
  val seedNumber: Long = 39
  val targetNumber: BigInt = BigInt(63245986)
  var computeCount: Int = 0

  def computeFibonacci(n: Long): BigInt = {
    computeCount = computeCount + 1
    val f = Fibonacci.tailrec(n)
    println(s"Computed fibonacci number, $f, from seed number, $n.")
    f
  }

  test("memoize") {
    val memo = Memo.immutableHashMapMemo {
      n: Long => computeFibonacci(n)
    }
    assert(memo(seedNumber) == targetNumber)  // Computed and cached.
    assert(memo(seedNumber) == targetNumber) // Cached value returned.
    assert(computeCount == 1) // Computed only once.
  }
}