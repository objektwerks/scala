package recursion

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.annotation.tailrec
import scala.util.control.TailCalls.{TailRec, done, tailcall}

class RecursionTest extends AnyFunSuite with Matchers {
  test("non-tailrec structural recursion") {
    def buildIntList(count: Int, component: Int): List[Int] = count match {
      case 0 => Nil
      case n => component :: buildIntList(n - 1, component)
    }
    buildIntList(3, 3) shouldEqual List(3, 3, 3)
  }

  test("non-tailrec list sum") {
    def sum(numbers: List[Int]): Int = if (numbers.isEmpty) 0 else numbers.head + sum(numbers.tail)
    val list = List(1, 2, 3)
    sum(list) shouldEqual list.sum
  }

  test("@tailrec list sum") {
    @tailrec
    def sum(numbers: List[Int], acc: Int = 0): Int = numbers match {
      case Nil => acc
      case head :: tail => sum(tail, acc + head)
    }
    val list = List(1, 2, 3)
    sum(list) shouldEqual list.sum
  }

  test("non-tailrec factorial") {
    def factorial(n: Long): Long = n match {
      case i if i < 1 => 1
      case _ => n * factorial(n - 1)
    }
    factorial(4) shouldEqual 24
  }

  test("@tailrec factorial") {
    @tailrec
    def factorial(n: Long, acc: Long = 1): Long = n match {
      case i if i < 1 => acc
      case _ => factorial(n - 1, acc * n)
    }
    factorial(4) shouldEqual 24
  }

  test("non-tailrec fibonacci") {
    def fibonacci(n: Long): BigInt = n match {
      case 0 | 1 => n
      case _ => fibonacci(n - 1) + fibonacci(n - 2)
    }
    val n = 34L
    val f = fibonacci(n)
    f.equals(BigInt(5702887)) shouldBe true
    println(s"Naive recursive fibonacci performance slows dramtically using > $n : $f")
  }

  test("@tailrec fibonacci") {
    @tailrec
    def fibonacci(n: Long, a: Long, b: Long): BigInt = n match {
      case 0 => a
      case _ => fibonacci(n - 1, b, a + b)
    }
    val n = 39L
    val f = fibonacci(n, 0, 1)
    f.equals(BigInt(63245986)) shouldBe true
    println(s"Tail recursive fibonacci performance is constant using <= $n : $f")
  }

  test("@tailrec fibonacci with inner def loop") {
    def fibonacci(n: Long): BigInt = {
      @tailrec
      def loop(n: Long, a: Long, b: Long): BigInt = n match {
        case 0 => a
        case _ => loop(n - 1, b, a + b)
      }
      loop(n, 0, 1)
    }
    val n = 39L
    val f = fibonacci(n)
    f.equals(BigInt(63245986)) shouldBe true
    println(s"@tailrec fibonacci performance is constant using <= $n : $f")
  }

  test("tailcall fibonacci") {
    def fibonacci(n: Long): TailRec[Long] = {
      if (n < 2) done(n)
      else for {
        x <- tailcall(fibonacci(n - 1))
        y <- tailcall(fibonacci(n - 2))
      } yield x + y
    }
    val n = 13L
    val f = fibonacci(n).result
    f shouldEqual 233
    println(s"@tailcalls ( trampolining ) fibonacci performance is horrible: $n : $f")
  }

  test("fibonacci sequence generator") {
    def fibonacci(a: Int = 0, b: Int = 1): List[Int] = {
      def build(a: Int, b: Int): LazyList[Int] = {
        LazyList.cons(a, build(b, a + b))
      }
      build(a, b).takeWhile(_ >= 0).toList
    }
    val sequence = fibonacci()
    sequence.size shouldEqual 47
    println("Fibonacci sequence: " + sequence)
  }

  test("non-tailrec split sum") {
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
    total shouldEqual 1783293664
  }
}