package matcher

import org.scalatest.FunSuite

class PatternMatchTest extends FunSuite {
  test("any match") {
    def isTrue(a: Any) = a match {
      case 0 | "" => false
      case _ => true
    }
    assert(isTrue(1))
    assert(!isTrue(0))
  }

  test("string match") {
    def isEqual(s: Int): String = s match {
      case 1 => "one"
      case 2 => "two"
      case _ => "many"
    }
    assert(isEqual(1) == "one")
    assert(isEqual(3) == "many")
  }
}