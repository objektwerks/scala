package theory

import org.scalatest.FunSuite

class CategoryTest extends FunSuite {
  test("applicative") {
    val optionApplicative = new Applicative[Option] {
      override def unit[A](a: => A): Option[A] = Option(a)
      override def apply[A, B](f: Option[(A) => B]): (Option[A]) => Option[B] = ???
      override def map[A, B](a: Option[A])(f: (A) => B): Option[B] = a map f
    }
    val option: Option[Int] = optionApplicative.unit(1)
    val mappedOption: Option[Int] = optionApplicative.map (option) (i => i * 3)
    assert(option.get == 1)
    assert(mappedOption.get == 3)
  }

  test("functor") {
    val toListOfStringsFunctor = new Functor[List] {
      override def map[A, B](xs: List[A])(f: A => B): List[B] = xs map f
    }
    val listOfNumbers = List(1, 2, 3)
    val listOfStrings = toListOfStringsFunctor.map(listOfNumbers)(_.toString)
    val expectedMorphism = List("1", "2", "3")
    assert(listOfStrings == expectedMorphism)
  }

  test("monad") {
    val optionMonad = new Monad[Option] {
      override def unit[A](a: => A): Option[A] = Option(a)
      override def flatten[A](a: Option[Option[A]]): Option[A] = a flatMap identity
      override def map[A, B](a: Option[A])(f: (A) => B): Option[B] = a map f
      override def flatMap[A, B](a: Option[A])(f: (A) => Option[B]): Option[B] = a flatMap f
    }
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
    val adder = new Monoid[Int] {
      override def id: Int = 0
      override def op(x: Int, y: Int): Int = x + y
      def fold(xs: List[Int]): Int = xs.fold(id)(op)
      def isValid(x: Int, y: Int, z: Int): Boolean = {
        val associative = op(op(x, y), z) == op(x, op(y, z))
        val identity = op(id, x) == x
        associative && identity
      }
    }
    assert(adder.op(1, 1) == 2)
    assert(adder.id == 0)
    assert(adder.fold(List(1, 2, 3)) == 6)
    assert(adder.isValid(1, 2, 3))
  }
}