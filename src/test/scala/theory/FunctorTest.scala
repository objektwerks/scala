package theory

import org.scalatest.FunSuite

class FunctorTest extends FunSuite {
  test("functor") {
    val nums = List(1, 2, 3)
    val strings = ToListOfStrings.map(nums)(_.toString)
    assert(strings == List("1", "2", "3"))
  }
}