package objektwerks.types

trait Semigroup[A] {
  def append(x: A, y: => A): A
}

trait Monoid[A] extends Semigroup[A] {
  def zero: A
}

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

trait Monad[F[_]] extends Functor[F] {
  def point[A](a: => A): F[A]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

trait Applicative[F[_]] extends Functor[F] {
  def point[A](a: => A): F[A]
  def apply[A, B](fa: F[A])(f: F[A => B]): F[B]
  override def map[A, B](fa: F[A])(f: A => B): F[B] = apply(fa)(point(f))
}

object CategoryTheory {
  val adderMonoid = new Monoid[Int] {
    override def zero: Int = 0
    override def append(x: Int, y: => Int): Int = x + y
  }

  val listFunctor = new Functor[List] {
    override def map[A, B](xs: List[A])(f: A => B): List[B] = xs map f
  }

  val optionMonad = new Monad[Option] {
    override def point[A](a: => A): Option[A] = Option(a)
    override def map[A, B](oa: Option[A])(f: A => B): Option[B] = oa map f
    override def flatMap[A, B](oa: Option[A])(f: A => Option[B]): Option[B] = oa flatMap f
  }

  val optionApplicative = new Applicative[Option] {
    override def point[A](a: => A): Option[A] = Some(a)
    override def apply[A, B](fa: Option[A])(ff: Option[A => B]): Option[B] = (fa, ff) match {
      case (None, _) => None
      case (Some(_), None) => None
      case (Some(a), Some(f)) => Some(f(a))
    }
  }

  def isAssociative[T](op: (T, T) => T, x: T, y: T, z: T): Boolean = {
    op(op(x, y), z) == op(x, op(y, z))
  }

  def isCommutative[T](op: (T, T) => T, x: T, y: T): Boolean = {
    op(x, y) == op(y, x)
  }

  def isIdempotent[T](op: T => T, x: T): Boolean = {
    val f = op
    val g = op compose op
    f(x) == g(x)
  }
}

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class CategoryTheoryTest extends AnyFunSuite with Matchers {
  import CategoryTheory._

  test("monoid") {
    assert(adderMonoid.zero == 0)
    assert(adderMonoid.append(1, 1) == 2)
  }

  test("functor") {
    val listOfNumbers = List(1, 2, 3)
    val listOfStrings = listFunctor.map(listOfNumbers)(_.toString)
    val expectedMorphism = List("1", "2", "3")
    assert(listOfStrings == expectedMorphism)
  }

  test("monad") {
    val option: Option[Int] = optionMonad.point(1)
    val mappedOption: Option[Int] = optionMonad.map(option)(i => i * 3)
    val flatMappedOption: Option[Int] = optionMonad.flatMap(option)(i => Some(i))
    option.get shouldEqual 1
    mappedOption.get shouldEqual 3
    flatMappedOption.get shouldEqual 1
    (option != mappedOption) shouldBe true
    option shouldEqual flatMappedOption
  }

  test("applicative") {
    val option: Option[Int] = optionApplicative.point(1)
    val mappedOption: Option[Int] = optionApplicative.map(option)(i => i * 3)
    option.get shouldEqual 1
    mappedOption.get shouldEqual 3
  }

  test("is associative") {
    isAssociative[Int](_ + _, 1, 2, 3) shouldBe true
    !isAssociative[Double](_ / _, 1, 2, 3) shouldBe true
  }

  test("is commutative") {
    isCommutative[Int](_ + _, 3, 6) shouldBe true
    !isCommutative[String](_ + _, "a", "b") shouldBe true
  }

  test("is idempotent") {
    def toUpper(s: String): String = s.toUpperCase
    def increment(i: Int) = i + 1
    isIdempotent(toUpper, "AbCdEfG") shouldBe true
    !isIdempotent(increment, 0) shouldBe true
  }
}