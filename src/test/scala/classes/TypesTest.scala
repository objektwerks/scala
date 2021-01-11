package classes

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

// Variance
trait Relative
class Parent extends Relative
class Child extends Parent

class Covariant[+R](val relative: R)
class Contravariant[-R, +S](val relative: S)
class Invariant[R](val relative: R)

trait NotNullFilter[-V, +R] { def notNull(value: V): R }

sealed trait Canine
class Dog extends Canine

// Bounds
object UpperBounds { def apply[U <: AnyVal](value: U): U = identity(value) }
object LowerBounds { def apply[L >: AnyVal](value: L): L = identity(value) }

// Compound Types
trait Init { def init: Boolean = true }
trait Run extends Init { def run: Boolean = init }
class Runnable extends Run { def isRunning: Boolean = run }
trait Emotion { def isEmoting: Boolean = true }
trait Speach { def isSpeaking: Boolean = true }
class Robot extends Runnable with Emotion with Speach

// Self Type
trait Speaking { def speaking: String }
trait Hello extends Speaking { override def speaking = "hello" }
trait Goodbye extends Speaking { override def speaking = "goodbye" }
class Speaker {
  self: Speaking =>
  def speak: String = speaking
}

// Path Dependent Types
class First {
  class Second
}

// Companion Object with Implict
case class Rational(numerator: Int, denominator: Int)
object Rational {
  implicit val ordering = Ordering.fromLessThan[Rational]((x, y) =>
    (x.numerator.toDouble / x.denominator.toDouble) <
      (y.numerator.toDouble / y.denominator.toDouble) )
}

class TypesTest extends AnyFunSuite with Matchers {
  test("variance") {
    val covariant: Covariant[Parent] = new Covariant[Child](new Child())
    val contravariant: Contravariant[Child, Parent] = new Contravariant[Child, Parent](new Parent())
    val invariant: Invariant[Child] = new Invariant[Child](new Child())

    covariant.relative.isInstanceOf[Child] shouldBe true
    contravariant.relative.isInstanceOf[Parent] shouldBe true
    invariant.relative.isInstanceOf[Child] shouldBe true
  }

  test("invariant") {
    class Vet[T](val canine: T)
    val dog: Dog = new Dog()
    val vet: Vet[Canine] = new Vet[Canine](dog)
    vet.canine.isInstanceOf[Canine] shouldBe true
    vet.canine.isInstanceOf[Dog] shouldBe true
  }

  test("covariant") {
    class Vet[+T](val canine: T)
    val dog: Dog = new Dog()
    val vet: Vet[Canine] = new Vet[Dog](dog)
    vet.canine.isInstanceOf[Canine] shouldBe true
    vet.canine.isInstanceOf[Dog] shouldBe true
  }

  test("contravariant") {
    class Vet[-T <: Canine](val canine: Canine)
    val dog = new Dog()
    val vet: Vet[Dog] = new Vet[Canine](dog)
    vet.canine.isInstanceOf[Canine] shouldBe true
    vet.canine.isInstanceOf[Dog] shouldBe true
  }  

  test("contravariant in, covariant out") {
    val filter = new NotNullFilter[String, Boolean] {
      override def notNull(value: String): Boolean = value != null
    }

    val values = List("a", "b", "c", null)
    val notNulls = values.filter(v => filter.notNull(v))

    notNulls shouldEqual List("a", "b", "c")
  }

  test("bounds") {
    val upperBounds: Int = UpperBounds(3)
    upperBounds shouldEqual 3

    val lowerBounds: Any = LowerBounds(3)
    lowerBounds shouldEqual 3
  }

  test("compound types") {
    val robot = new Robot()
    robot.isRunning shouldBe true
    robot.isEmoting shouldBe true
    robot.isSpeaking shouldBe true
  }

  test("type alias") {
    type User = String
    type Age = Int
    val users:  Map[User, Age] =  Map("john" -> 21, "jane" -> 19)

    users("john") shouldEqual 21
    users("jane") shouldEqual 19
  }

  test("duck typing") {
    class Greeter { def greet = "Hi!" }
    def greet(greeter: { def greet: String } ): String = greeter.greet
    
    greet(new Greeter) shouldEqual "Hi!"
  }

  test("self type") {
    val helloSpeaker = new Speaker() with Hello
    helloSpeaker.speak shouldEqual "hello"

    val goodbyeSpeaker = new Speaker() with Goodbye
    goodbyeSpeaker.speak shouldEqual "goodbye"
  }

  test("path dependent types") {
    val first1 = new First()
    val path1 = new first1.Second()
    val first2 = new First()
    val path2 = new first2.Second()
    path1 should not equal path2
  }

  test("type instances") {
    val minOrdering: Ordering[Int] = Ordering.fromLessThan[Int](_ < _)
    val maxOrdering: Ordering[Int] = Ordering.fromLessThan[Int](_ > _)

    List(3, 4, 2).sorted(minOrdering) shouldEqual List( 2, 3, 4)
    List(3, 4, 2).sorted(maxOrdering) shouldEqual List(4, 3, 2)
  }

  test("implicit type instance") {
    implicit val ordering: Ordering[String] = Ordering.fromLessThan[String](_ < _)

    List("c", "b", "a").sorted shouldEqual List("a", "b", "c")
  }

  test("companion object with implicit") {
    List(Rational(1, 2), Rational(3, 4), Rational(1, 3)).sorted shouldEqual List(Rational(1, 3), Rational(1, 2), Rational(3, 4))
  }
}