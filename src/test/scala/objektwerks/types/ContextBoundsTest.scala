package objektwerks.types

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ContextBoundsTest extends AnyFunSuite with Matchers {
  def maxComparator[A: Ordering](a: A, b: A): A = {
    val ordering = implicitly[Ordering[A]]
    ordering.max(a, b)
  }

  test("context bounds") {
    maxComparator(1, 2) shouldBe 2
  }
}