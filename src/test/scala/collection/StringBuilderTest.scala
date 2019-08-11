package collection

import org.scalatest.FunSuite

class StringBuilderTest extends FunSuite {
  test("string builder") {
    val builder = new StringBuilder
    builder.append("a")
    builder.append("b")
    builder.append("c")
    assert(builder.toString() == "abc")
    assert(builder.result() == "abc")
    assert(builder.reverse.result() == "cba")
  }
}