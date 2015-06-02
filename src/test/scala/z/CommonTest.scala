package z

import org.scalatest.FunSuite

import scalaz.Scalaz._
import scalaz._

class CommonTest extends FunSuite {
  test("disjunction") {
    "success".right.merge assert_=== "success"
    "failure".left.merge assert_=== "failure"

    \/.right("success").merge assert_=== "success"
    \/.left("failure").merge assert_=== "failure"

    \/-("success").merge assert_=== "success"
    -\/("failure").merge assert_=== "failure"

    false /\ true assert_=== false
    false \/ true assert_=== true
  }

  test("enum") {
    val enum = 'a' |-> 'z'
    enum.byName(0) assert_=== 'a'
    enum.byName(enum.length - 1) assert_=== 'z'
    enum.head assert_=== 'a'
    enum.last assert_=== 'z'
    enum.contains('m') assert_=== true
    'o'.succ assert_=== 'p'
    'o'.pred assert_=== 'n'
  }

  test("bounded enum") {
    implicitly[Enum[Int]].min assert_=== Some(-2147483648)
    implicitly[Enum[Int]].max assert_=== Some(2147483647)
  }

  test("equal") {
    Some(1) === Some(1) assert_=== true
    1.some =/= 2.some assert_=== true // not equal test
  }

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

  test("order") {
    1 > 2 assert_=== false
    1 min 2 assert_=== 1
    1 max 2 assert_=== 2
    2 gt 1 assert_=== true
    1 lt 2 assert_=== true
    2 gte 1 assert_=== true
    1 lte 2 assert_=== true
  }

  test("show") {
    1.show.toString assert_=== "1"
    1.shows assert_=== "1"
  }
}