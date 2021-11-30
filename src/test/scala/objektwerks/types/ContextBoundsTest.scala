package objektwerks.types

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ContextBoundsTest extends AnyFunSuite with Matchers {
  test("context bounds") {
    def maximum[A: Ordering](a: A, b: A): A = {
      val ordering = implicitly[Ordering[A]]
      ordering.max(a, b)
    }

    maximum( 1, 2 ) shouldBe 2
  }
}