package z

import org.scalatest.FunSuite

class FunctionalTest extends FunSuite {
  /*
   1. What makes a function pure? Give examples.
      A. Side-effect free functions. See below.
   2. What is referential transparency? What are its benefits?
      A. Side-effect free functions and modular apps. See below.
   3. How does a functional program deal with side-effects?
      A. Pure functions or push it to the edge of an application. I've only finished part I of Functional Programming
      in Scala. But it promises to answer how a pure functional IO solution is superior to Akka actors. See Paul's blog
      for an interesting back-n-forth chat on this topic with Roland Kuhn and others.
  */

  test("impure function") {
    def add(x: Int, y: Int): Int = {
      val sum = x + y
      println(sum) // simulating side-effecting IO
      sum
    }
    add(1, 2)
  }

  test("pure function") {
    def add(x: Int, y: Int): Int = {
      x + y
    }
    add(1, 2) /* remove the side-effecting IO, and return the result. add(1, 2) could be replaced with the result 5,
                 making it referentiallly transparent. See Functional Programming in Scala for details.:) */
  }
}