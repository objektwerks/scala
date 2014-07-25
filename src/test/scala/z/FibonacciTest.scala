package z

import org.scalatest.FunSuite

import scala.annotation.tailrec

class FibonacciTest extends FunSuite {
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
    def fibonacci(n: Long): BigInt = {
      @tailrec
      def loop(n: Long, a: Long, b: Long): BigInt = n match {
        case 0 => a
        case _ => loop(n - 1, b, a + b)
      }
      loop(n, 0, 1)
    }
    val n = 39
    val f = fibonacci(n)
    assert(f.equals(BigInt(63245986)))
    println(s"@tailrec performance is constant using any number >= $n == $f")
  }

  test("fibonacci sequece generator") {
    def fibonacciSequence(a: Int = 0, b: Int = 1): Stream[Int] = Stream.cons(a, fibonacciSequence(b, a + b))
    val sequence = fibonacciSequence().takeWhile(_>= 0).toList
    assert(sequence.size == 47)
    println("Fibonacci sequence: " + sequence)
  }
}