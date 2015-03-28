package theory

import org.scalatest.FunSuite

class FunctorTest extends FunSuite {
  test("functor") {
    val listOfNumbers = List(1, 2, 3)
    val listOfStrings = ToListOfStrings.map(listOfNumbers)(_.toString)
    val expectedMorphism = List("1", "2", "3")
    assert(listOfStrings == expectedMorphism)
  }
}