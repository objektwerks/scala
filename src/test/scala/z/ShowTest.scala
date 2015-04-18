package z

import org.scalatest.FunSuite

import scalaz.Scalaz._

class ShowTest extends FunSuite {
  test("show") {
    1.show.toString assert_=== "1"
    1.shows assert_=== "1"
  }
}