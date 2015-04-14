package z

import org.scalatest.FunSuite

import scalaz._
import Scalaz._

class CommonTest extends FunSuite {
  test("enum") {
    val enum = 'a' |-> 'z'
    enum.byName(0) assert_=== 'a'
    enum.byName(enum.length - 1) assert_=== 'z'
    enum.head assert_=== 'a'
    enum(enum.length - 1) assert_=== 'z'
    enum.contains('m') assert_=== true
    'o'.succ assert_=== 'p'
    'o'.pred assert_=== 'n'
  }

  test("bounded") {
    implicitly[Enum[Int]].min assert_=== Some(-2147483648)
    implicitly[Enum[Int]].max assert_=== Some(2147483647)
  }

  test("equal") {
    Some(1) === Some(1) assert_=== true
    1.some =/= 2.some assert_=== true // not equal test
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