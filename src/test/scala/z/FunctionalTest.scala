package z

import org.scalatest.FunSuite

// See Functional Programming in Scala for details.
class FunctionalTest extends FunSuite {
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