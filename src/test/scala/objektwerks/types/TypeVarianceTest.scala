package objektwerks.types

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

sealed trait Canine
class Dog extends Canine
class Wolf extends Canine

class TypeVarianceTest extends AnyFunSuite with Matchers {
  test("invariant") {
    class Vet[T] {
      def heal[T](canine: T): T = canine
    }

    val canineVet = new Vet[Canine]
    canineVet.heal[Canine]( new Dog() ).isInstanceOf[Dog] shouldBe true
    canineVet.heal[Canine]( new Wolf() ).isInstanceOf[Wolf] shouldBe true

    val dogVet: Vet[Dog] = new Vet[Dog]
    dogVet.heal[Dog]( new Dog() ).isInstanceOf[Dog] shouldBe true

    val wolfVet: Vet[Wolf] = new Vet[Wolf]
    wolfVet.heal[Wolf]( new Wolf() ).isInstanceOf[Wolf] shouldBe true
  }

  test("covariant") {
    class Vet[+T] {
      def heal[S >: T](canine: S): S = canine
    }

    val canineVet = new Vet[Canine]
    canineVet.heal[Canine]( new Dog() ).isInstanceOf[Dog] shouldBe true
    canineVet.heal[Canine]( new Wolf() ).isInstanceOf[Wolf] shouldBe true

    val dogVet: Vet[Dog] = new Vet[Dog]
    dogVet.heal[Dog]( new Dog() ).isInstanceOf[Dog] shouldBe true

    val wolfVet: Vet[Wolf] = new Vet[Wolf]
    wolfVet.heal[Wolf]( new Wolf() ).isInstanceOf[Wolf] shouldBe true
  }

  test("contravariant") {
    class Vet[-T] {
      def heal[S <: T](canine: S): S = canine
    }

    val canineVet = new Vet[Canine]
    canineVet.heal[Canine]( new Dog() ).isInstanceOf[Dog] shouldBe true
    canineVet.heal[Canine]( new Wolf() ).isInstanceOf[Wolf] shouldBe true

    val dogVet: Vet[Dog] = new Vet[Canine]
    dogVet.heal[Dog]( new Dog() ).isInstanceOf[Dog] shouldBe true

    val wolfVet: Vet[Wolf] = new Vet[Canine]
    wolfVet.heal[Wolf]( new Wolf() ).isInstanceOf[Wolf] shouldBe true
  }

  test("contravariant in, covariant out") {
    trait Function[-V, +R] {
      def apply(value: V): R
    }

    val function = new Function[String, Option[Int]] {
      def apply(value: String): Option[Int] = value.toIntOption
    }

    val values = List("1", "2", "3", "four")
    values.flatMap(value => function(value)) shouldEqual List(1, 2, 3)
    values.flatMap(value => function(value)).sum shouldEqual 6
  }
}