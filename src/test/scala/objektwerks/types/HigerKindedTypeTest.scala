package objektwerks.types

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class HigerKindedTypeTest extends AnyFunSuite with Matchers {
  trait Functor[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
  }

  val listFunctor = new Functor[List] {
    override def map[A, B](xs: List[A])(f: A => B): List[B] = xs map f
  }

  test("higher kinded") {
    val listOfNumbers = List(1, 2, 3)
    val listOfStrings = listFunctor.map(listOfNumbers)(_.toString)
    val expectedMorphism = List("1", "2", "3")
    assert(listOfStrings == expectedMorphism)
  }
}