package z

import function.Fibonacci
import org.scalatest.FunSuite

import scalaz._

class MemoizationTest extends FunSuite {
  val fibonacciNumber: Long = 39

  def computeFibonacciNumber(n: Long): BigInt = {
    val f = Fibonacci.number(n)
    println(s"Computed fibonacci number from $n to $f")
    f
  }

  test("memoize") {
    val memo = Memo.immutableHashMapMemo {
      n: Long => computeFibonacciNumber(n)
    }
    memo(fibonacciNumber)   // Computed and cached.
    memo(fibonacciNumber)  // Cached value returned.
  }
}