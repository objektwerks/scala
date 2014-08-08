package z

import function.Fibonacci
import org.scalatest.FunSuite

import scalaz._

class MemoizationTest extends FunSuite {
  val fibonacciSeed: Long = 39
  val fibonacciNumber: BigInt = BigInt(63245986)
  var fibonacciNumberComputedCount: Int = 0

  def computeFibonacciNumber(n: Long): BigInt = {
    fibonacciNumberComputedCount = fibonacciNumberComputedCount + 1
    val f = Fibonacci.number(n)
    println(s"Computed fibonacci number from $n to $f")
    f
  }

  test("memoize") {
    val memo = Memo.immutableHashMapMemo {
      n: Long => computeFibonacciNumber(n)
    }
    assert(memo(fibonacciSeed) == fibonacciNumber)  // Computed and cached.
    assert(memo(fibonacciSeed) == fibonacciNumber) // Cached value returned.
    assert(fibonacciNumberComputedCount == 1)
  }
}