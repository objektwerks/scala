package function

import org.scalatest.FunSuite

import scala.annotation.tailrec
import scala.language.reflectiveCalls
import scala.util.Random

class FunctionTest extends FunSuite {
  test ("anonymous") {
    val v = Vector(1, 2, 3, 4)
    assert(v.filter(_ % 2 == 0) == Vector(2, 4))
  }

  test("type implicit") {
    val add = (x: Int, y: Int) => x + y
    assert(add(3, 3) == 6)
  }

  test("type explicit") {
    val subtract: (Int, Int) => Int = (x, y) => x - y
    assert(subtract(9, 3) == 6)
  }

  test("defined") {
    def isEven(i: Int): Boolean = i % 2 == 0
    assert(isEven(2))
  }

  test("call by value") {
    def random: Long = Random.nextLong()
    def callByValue(r: Long): (Long, Long) = (r, r)
    val r = callByValue(random)
    assert(r._1 == r._2)
  }

  test("call by name") {
    def random: Long = Random.nextLong()
    def callByName(r: => Long): (Long, Long) = (r, r)
    val r = callByName(random)
    assert(r._1 != r._2)
  }

  test("default args") {
    def multiply(x: Int, y: Int = 1): Int = x * y
    assert(multiply(1) == 1)
  }

  test("var args") {
    def add(varargs: Int*): Int = varargs.sum
    assert(add(1, 2, 3) == 6)
  }

  test("closure") {
    val drinkingAge = 21
    val canDrink = (age: Int) => age >= drinkingAge
    assert(canDrink(21))
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

  test("returning a function") {
    def greeting(greeting: String) = (name: String) => {
      greeting + ", " + name + "!"
    }
    val hello = greeting("Hello")
    assert(hello("John") == "Hello, John!")
  }

  test("curry") {
    def multiply(x: Int)(y: Int) = x * y
    assert(multiply(3)(3) == 9)
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
}