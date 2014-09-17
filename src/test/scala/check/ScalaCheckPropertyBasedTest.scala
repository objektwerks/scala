package check

import org.scalacheck.Prop.forAll
import org.scalatest.FunSuite

class ScalaCheckPropertyBasedTest extends FunSuite {
  test("boolean check") {
    val propListSize = forAll { (xs: List[Int], ys: List[Int]) => xs.size + ys.size == (xs ::: ys).size }
    propListSize.check
  }
}