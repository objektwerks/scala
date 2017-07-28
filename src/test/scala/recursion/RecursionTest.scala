package recursion

import org.scalatest.FunSuite

import scala.annotation.tailrec

/**
  * Structural Recursion Template:
  *
  * def name(count: Int): Result =
        count match {
          case 0 => resultBase
          case n => resultUnit add name(n-1)
        }
  */
class RecursionTest extends FunSuite {
  test("@tailrec int accumulator") {
    @tailrec
    def sum(number: Int, acc: Int = 0): Int = number match {
      case 0 => acc
      case n => sum(n - 1, acc + n)
    }
    assert(sum(3) == 6)
  }

  test("recursive list sum") {
    def sum(numbers: List[Int]): Int = numbers match {
      case Nil => 0
      case head :: tail => head + sum(tail)
    }
    assert(sum(List(1, 2, 3)) == 6)
  }

  test("non tail callable factorial") {
    assert(Factorial.nonTailCallable(4) == 24)
  }

  test("tail callable factorial") {
    assert(Factorial.tailCallable(4) == 24)
  }

  test("naive recursive fibonacci") {
    val n = 34
    val f = Fibonacci.naiveRecursive(n)
    assert(f.equals(BigInt(5702887)))
    println(s"Naive recursive fibonacci performance slows dramtically using > $n : $f")
  }

  test("tail recursive fibonacci") {
    val n = 39
    val f = Fibonacci.tailRecursive(n, 0, 1)
    assert(f.equals(BigInt(63245986)))
    println(s"Tail recursive fibonacci performance is constant using <= $n : $f")
  }

  test("@tailrec fibonacci") {
    val n = 39
    val f = Fibonacci.tailRecursiveWithLoop(n)
    assert(f.equals(BigInt(63245986)))
    println(s"@tailrec fibonacci performance is constant using <= $n : $f")
  }

  test("tailcalls fibonacci") {
    val n = 13
    val f = Fibonacci.tailcalls(n).result
    assert(f == 233)
    println(s"@tailcalls ( trampolining ) fibonacci performance is horrible: $n : $f")
  }

  test("fibonacci sequece generator") {
    val sequence = Fibonacci.sequence()
    assert(sequence.size == 47)
    println("Fibonacci sequence: " + sequence)
  }

  test("recursive split sum") {
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
}