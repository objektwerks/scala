package function

import math.Factorial
import org.scalatest.FunSuite

class FactorialTest extends FunSuite{
  test("non tail callable factorial") {
    assert(Factorial.nonTailCallableFactorial(4) == 24)
  }

  test("tail callable factorial") {
    assert(Factorial.tailCallableFactorial(4) == 24)
  }
}