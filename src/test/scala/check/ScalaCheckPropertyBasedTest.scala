package check

import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Prop}
import org.scalatest.FunSuite

class ScalaCheckPropertyBasedTest extends FunSuite {
  test("check") {
    val propListSize = forAll {
      (xs: List[Int], ys: List[Int]) => xs.size + ys.size == (xs ::: ys).size
    }
    propListSize.check
  }

  test("generator check") {
    val smallInteger = Gen.choose(0, 100)
    val propSmallInteger = Prop.forAll(smallInteger)(n => n >= 0 && n <= 100)
    propSmallInteger.check
  }
}