package function

import org.scalatest.FunSuite

import scala.annotation.tailrec
import scala.language.reflectiveCalls
import scala.util.Random

class FunctionTest extends FunSuite {
  test("literal") {
    val add = (x: Int, y: Int) => x + y
    assert(add(3, 3) == 6)

    val multiply = (x: Int, y: Int) => x * y: Int
    assert(multiply(3, 3) == 9)

    val subtract: (Int, Int) => Int = (x, y) => x - y
    assert(subtract(9, 3) == 6)
  }

  test("def expression") {
    def isEven(i: Int): Boolean = i % 2 == 0
    assert(isEven(2))
  }

  test("def body") {
    def isOdd(i: Int): Boolean = {
      i % 2 != 0
    }
    assert(isOdd(3))
  }

  test("def match") {
    def sum(xs: List[Int]): Int = xs match {
      case head :: tail => head + sum(tail)
      case Nil => 0
    }
    assert(sum(List(1, 2, 3)) == 6)
  }

  test("def returning a function") {
    def greeting(greeting: String) = (name: String) => {
      greeting + ", " + name + "!"
    }
    val hello = greeting("Hello")
    assert(hello("John") == "Hello, John!")
  }

  test("call by value") {
    def callByValue(r: Long): (Long, Long) = (r, r)
    val (r1, r2) = callByValue(Random.nextLong())
    assert(r1 == r2)
  }

  test("call by name") {
    def callByName(r: => Long): (Long, Long) = (r, r)
    val (r1, r2) = callByName(Random.nextLong())
    assert(r1 != r2)
  }

  test("default args") {
    def multiply(x: Int, y: Int = 1): Int = x * y
    assert(multiply(1) == 1)
  }

  test("var args") {
    def add(varargs: Int*): Int = varargs.sum
    assert(add(1, 2, 3) == 6)
    assert(add(List(1, 2, 3):_*) == 6 )
  }

  test("closure") {
    val legalDrinkingAge = 21
    def isLegallyOldEnoughToDrink(age: Int): Boolean = age >= legalDrinkingAge
    assert(isLegallyOldEnoughToDrink(22))
  }

  test("higher order") {
    def square(f: Int => Int, i: Int) = f(i)
    assert(square((x: Int) => x * x, 2) == 4)
  }

  test("partially applied") {
    def multiplier(multiplier: Int) = (i: Int) => multiplier * i
    val multiplyByFive = multiplier(5)
    assert(multiplyByFive(20) == 100)
  }

  test("partial function") {
    val fraction = new PartialFunction[Int, Int] {
      def apply(d: Int) = 2 / d
      def isDefinedAt(d: Int): Boolean = d <= 0
    }
    assert(fraction(2) == 1)
    assert(fraction.isDefinedAt(-42))
  }

  test("curry") {
    def multiply(x: Int) = (y: Int) => x * y
    assert(multiply(3)(3) == 9)
  }

  test ("lambda") {
    val list = List(1, 2, 3, 4)
    assert(list.filter(_ % 2 == 0) == List(2, 4))
  }

  test("recursion") {
    def factorial(n: Int): Int = n match {
      case i if i <= 0 => 1
      case _ => n * factorial(n - 1)
    }
    assert(factorial(3) == 6)
  }

  test("tailrec") {
    @tailrec
    def factorial(n: Int, acc: Int = 1): Int = n match {
      case i if i <= 0 => acc
      case _ => factorial(n - 1, acc * n)
    }
    assert(factorial(9) == 362880)
  }

  test("impure function") {
    def add(x: Int, y: Int): Int = {
      val sum = x + y
      println(sum) // Simulating side-effecting IO
      sum
    }
    add(1, 2)
  }

  test("pure function") {
    def add(x: Int, y: Int): Int = {
      x + y // No side-effecting IO.
    }
    add(1, 2)
  }

  test("compose > andThen") {
    val incr = (n: Int) => n + 1
    val decr = (n: Int) => n - 1
    val composedIncrDecr = incr compose decr
    val andThenIncrDecr = incr andThen decr
    val functions = List(incr, decr)
    val uberFunction = functions reduce ( _ andThen _ )

    val xs = 1 to 10 toList
    val ys = xs map incr map decr
    val zs = xs map composedIncrDecr map andThenIncrDecr
    val fs = xs map ( functions reduce ( _ compose _) )
    val gs = xs map ( functions reduce ( _ andThen _) )
    val us = xs map uberFunction
    assert(xs == ys)
    assert(ys == zs)
    assert(fs == zs)
    assert(gs == fs)
    assert(us == gs)
  }
}