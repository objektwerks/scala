package z

import org.scalatest.FunSuite

import scalaz.Scalaz._
import scalaz._

class OptionTest extends FunSuite {
  test("option") {
    1.some assert_=== Some(1)
    none[Int] assert_=== None
  }

  test("filter") {
    some("true") assert_=== (1 < 2).option("true")
    none[String] assert_=== (2 < 1).option("false")
  }

  test("combine") {
    3.some assert_=== 1.some |+| 2.some
    "scalaz".some assert_=== "scala".some |+| "z".some
  }

  test("get or else") {
    1.some? 1 | 0 assert_=== 1
    none? 1 | 0 assert_=== 0

    1.some | 2 assert_=== 1
    none | 2 assert_=== 2
  }
}