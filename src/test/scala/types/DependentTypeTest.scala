package types

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class First {
  class Second
}

class DependentTypeTest extends AnyFunSuite with Matchers {
  test("dependent type") {
    val firstDependent1 = new First()
    val firstToSecondDependentPath1 = new firstDependent1.Second()

    val firstDependent2 = new First()
    val firstToSecondDependentPath2 = new firstDependent2.Second()

    firstToSecondDependentPath1 should not equal firstToSecondDependentPath2
  }
}