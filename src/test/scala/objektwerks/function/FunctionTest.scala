package objektwerks.function

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.annotation.tailrec
import scala.util.Random
import scala.util.chaining._
import scala.language.postfixOps

class FunctionTest extends AnyFunSuite with Matchers {
  test("literal") {
    val add = (x: Int, y: Int) => x + y
    add(3, 3) shouldEqual 6

    val multiply = (x: Int, y: Int) => x * y: Int
    multiply(3, 3) shouldEqual 9

    val subtract: (Int, Int) => Int = (x, y) => x - y
    subtract(9, 3) shouldEqual 6
  }

  test("def expression") {
    def isEven(i: Int): Boolean = i % 2 == 0
    isEven(2) shouldBe true
  }

  test("def block") {
    def isOdd(i: Int): Boolean = {
      i % 2 != 0
    }
    isOdd(3) shouldBe true
  }

  test("def match") {
    def sum(xs: List[Int]): Int = xs match {
      case Nil => 0
      case head :: tail => head + sum(tail)
    }
    sum(List(1, 2, 3)) shouldEqual 6
  }

  test("currying") {
    def greeting(greeting: String): String => String = (name: String) => {
      greeting + ", " + name + "!"
    }
    val hello = greeting("Hello")
    hello("John") shouldEqual "Hello, John!"
  }

  test("call by value") {
    def callByValue(r: Long): (Long, Long) = (r, r)
    val (r1, r2) = callByValue(Random.nextLong())
    r1 shouldEqual r2
  }

  test("call by name") {
    def callByName(r: => Long): (Long, Long) = (r, r)
    val (r1, r2) = callByName(Random.nextLong())
    r1 should not equal r2
  }

  test("default args") {
    def multiply(x: Int, y: Int = 1): Int = x * y
    multiply(1) shouldEqual 1
  }

  test("var args") {
    def add(varargs: Int*): Int = varargs.sum
    add(1, 2, 3) shouldEqual 6
    add(List(1, 2, 3):_*) shouldEqual 6
  }

  test("closure") {
    val legalDrinkingAge = 21
    def isLegallyOldEnoughToDrink(age: Int): Boolean = age >= legalDrinkingAge
    isLegallyOldEnoughToDrink(22) shouldBe true
  }

  test("higher order") {
    def square(f: Int => Int, i: Int) = f(i)
    square((x: Int) => x * x, 2) shouldEqual 4
  }

  test("partially applied") {
    def multiplier(x: Int, y: Int): Int = x * y
    val product = multiplier _
    val multiplyByFive = multiplier(5, _: Int)
    product(5, 20) shouldEqual 100
    multiplyByFive(20) shouldEqual 100
  }

  test("partial function") {
    val fraction = new PartialFunction[Int, Int] {
      def apply(d: Int) = 2 / d
      def isDefinedAt(d: Int): Boolean = d <= 0
    }
    fraction(2) shouldEqual 1
    fraction.isDefinedAt(-42) shouldBe true
  }

  test("curry") {
    def multiply(x: Int): Int => Int = (y: Int) => x * y
    multiply(3)(3) shouldEqual 9

    def add(x: Int)(y: Int): Int = x + y
    add(1)(2) shouldEqual 3
  }

  test("curried") {
    val sum = (x: Int, y: Int) => x + y
    val curriedSum = sum.curried
    curriedSum(1)(2) shouldEqual 3
  }

  test ("lambda") {
    val list = List(1, 2, 3, 4)
    list.filter(_ % 2 == 0) shouldEqual List(2, 4)
  }

  test("non-tailrec") {
    def factorial(n: Int): Int = n match {
      case i if i < 1 => 1
      case _ => n * factorial(n - 1)
    }
    factorial(3) shouldEqual 6
  }

  test("tailrec") {
    @tailrec
    def factorial(n: Int, acc: Int = 1): Int = n match {
      case i if i < 1 => acc
      case _ => factorial(n - 1, acc * n)
    }
    factorial(9) shouldEqual 362880
  }

  test("impure function") {
    def add(x: Int, y: Int): Int = {
      val sum = x + y
      println(sum) // Simulating side-effecting IO
      sum
    }
    add(1, 2) shouldEqual 3
  }

  test("pure function") {
    def add(x: Int, y: Int): Int = {
      x + y // No side-effecting IO.
    }
    add(1, 2) shouldEqual 3
  }

  test("compose > andThen") {
    val incr = (n: Int) => n + 1
    val decr = (n: Int) => n - 1
    val incrComposeDecr = incr compose decr
    val incrAndThenDecr = incr andThen decr
    val incrDecrAsList = List(incr, decr)
    val incrDecrAsListWithReduce = incrDecrAsList reduce ( _ andThen _ )

    val xs = (1 to 10).toList
    val ys = xs map incr map decr
    val zs = xs map incrComposeDecr map incrAndThenDecr
    val fs = xs map ( incrDecrAsList reduce ( _ compose _ ) )
    val gs = xs map ( incrDecrAsList reduce ( _ andThen _ ) )
    val us = xs map incrDecrAsListWithReduce
    xs shouldEqual ys
    ys shouldEqual zs
    fs shouldEqual zs
    gs shouldEqual fs
    us shouldEqual gs
  }

  test("pipe") {
    val square = (n: Int) => n * n
    assert( 2.pipe(square) == 4 )
  } 

  test("select by index") {
    def selectByIndex(source: List[Int], index: Int): Option[Int] = {
      @tailrec
      def loop(source: List[Int], index: Int, acc: Int = 0): Option[Int] = source match {
        case Nil => None
        case head :: tail => if (acc == index) Some(head) else loop(tail, index, acc + 1)
      }
      loop(source, index)
    }
    val xs = 1 to 10 toList
    val ys = List[Int]()
    val zs = List(1, 2, 3, 4)
    val x = selectByIndex(xs, 5)
    val y = selectByIndex(ys, 5)
    val z = selectByIndex(zs, 5)
    x.get shouldEqual xs(5)
    y.isEmpty shouldBe true
    z.isEmpty shouldBe true
  }

  test("intersection") {
    def intersection(source: List[Int], target: List[Int]): List[Int] = {
      for (s <- source if target.contains(s)) yield s
    }
    val xs = List.range(1, 10)
    val ys = List.range(1, 20)
    val zs = List.range(30, 40)
    intersection(xs, ys) shouldEqual xs.intersect(ys)
    intersection(ys, xs) shouldEqual ys.intersect(xs)
    intersection(ys, zs) shouldEqual ys.intersect(zs)
  }
}