package z

import org.scalatest.FunSuite

import scalaz.Scalaz._
import scalaz._

class EqualTest extends FunSuite {
  test("equal") {
    Some(1) === Some(1) assert_=== true
    1.some =/= 2.some assert_=== true // not equal test
  }
}