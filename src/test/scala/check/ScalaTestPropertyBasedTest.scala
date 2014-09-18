package check

import org.scalatest.FunSuite
import org.scalatest.Matchers._
import org.scalatest.prop.{Checkers, GeneratorDrivenPropertyChecks}

class ScalaTestPropertyBasedTest extends FunSuite with Checkers with GeneratorDrivenPropertyChecks {
  implicit override val generatorDrivenConfig = PropertyCheckConfig(minSuccessful = 10, maxDiscarded = 100, minSize = 10, maxSize = 100, workers = 1)

  test("check") {
    check((xs: List[Int], ys: List[Int]) => xs.size + ys.size == (xs ::: ys).size)
  }

  test("generator check") {
    forAll {
      (n: Int) => whenever (n > 1) { n / 2 should be > 0 }
    }
  }
}