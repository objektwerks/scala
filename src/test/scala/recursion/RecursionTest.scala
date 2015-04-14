package recursion

import math.{Fibonacci, Factorial}
import org.scalatest.FunSuite

class RecursionTest extends FunSuite{
  test("non tail callable factorial") {
    assert(Factorial.nonTailCallableFactorial(4) == 24)
  }

  test("tail callable factorial") {
    assert(Factorial.tailCallableFactorial(4) == 24)
  }

  test("naive recursive fibonacci") {
    def fibbonacci(n : Long) : BigInt = n match {
      case 0 | 1 => n
      case _ => fibbonacci(n - 1) + fibbonacci(n - 2)
    }
    val n = 34
    val f = fibbonacci(n)
    assert(f.equals(BigInt(5702887)))
    println(s"Naive recursive performance slows dramtically beyond $n == $f")
  }

  test("tail recursive fibonacci") {
    def fibonacci(n: Long, a: Long, b: Long): BigInt = n match {
      case 0 => a
      case _ => fibonacci(n - 1, b, a + b)
    }
    val n = 39
    val f = fibonacci(n, 0, 1)
    assert(f.equals(BigInt(63245986)))
    println(s"Tail recursive performance is constant using any number >= $n == $f")
  }

  test("@tailrec fibonacci") {
    val n = 39
    val f = Fibonacci.number(n)
    assert(f.equals(BigInt(63245986)))
    println(s"@tailrec performance is constant using any number >= $n == $f")
  }

  test("fibonacci sequece generator") {
    val sequence = Fibonacci.sequence().takeWhile(_>= 0).toList
    assert(sequence.size == 47)
    println("Fibonacci sequence: " + sequence)
  }
}