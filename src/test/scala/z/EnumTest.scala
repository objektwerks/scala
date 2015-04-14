package z

import org.scalatest.FunSuite

import scalaz._
import Scalaz._

class EnumTest extends FunSuite {
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
}