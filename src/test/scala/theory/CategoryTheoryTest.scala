package theory

import org.scalatest.FunSuite
import theory.CategoryTheory._

class CategoryTheoryTest extends FunSuite {
  test("applicative") {
    val option: Option[Int] = optionApplicative.unit(1)
    val mappedOption: Option[Int] = optionApplicative.map (option) (i => i * 3)
    assert(option.get == 1)
    assert(mappedOption.get == 3)
  }

  test("functor") {
    val listOfNumbers = List(1, 2, 3)
    val listOfStrings = listFunctor.map(listOfNumbers)(_.toString)
    val expectedMorphism = List("1", "2", "3")
    assert(listOfStrings == expectedMorphism)
  }

  test("monad") {
    val option: Option[Int] = optionMonad.unit(1)
    val mappedOption: Option[Int] = optionMonad.map (option) (i => i * 3)
    val flattenedOption: Option[Int] = optionMonad.flatten(Option(option))
    val flatMappedOption: Option[Int] = optionMonad.flatMap (option) (i => Some(i))
    assert(option.get == 1)
    assert(flattenedOption.get == 1)
    assert(mappedOption.get == 3)
    assert(flatMappedOption.get == 1)
    assert(option != mappedOption)
    assert(option == flatMappedOption)
  }

  test("monoid") {
    assert(adderMonoid.op(1, 1) == 2)
    assert(adderMonoid.id == 0)
    assert(adderMonoid.fold(List(1, 2, 3)) == 6)
    assert(adderMonoid.isValid(1, 2, 3))
  }
}