package matcher

import org.scalatest.FunSuite

class MatchTest extends FunSuite {
  test("match") {
    def isTrue(a: Any) = a match {
      case 0 | "" => false
      case _ => true
    }
    assert(isTrue(1))
    assert(!isTrue(0))
  }
}