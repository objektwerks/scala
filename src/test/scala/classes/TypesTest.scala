package classes

import org.scalatest.FunSuite

// Variance
trait Relative
class Parent extends Relative
class Child extends Parent
class Covariant[+RR](val relative: RR)
class Contravariant[-RR, +SS](val relative: SS)
class Invariant[RR](val relative: RR)
trait PositiveFilter[-AA, +BB] { def isPositive(n: Int): Boolean }

// Bounds
object UpperBounds { def apply[UB <: AnyVal](n: UB): UB = identity(n) }
object LowerBounds { def apply[LB >: AnyVal](n: LB): LB = identity(n) }

// Compound Types
trait Init { def init: Boolean = true }
trait Run extends Init { def run: Boolean = init }
class Runnable extends Run {
  def isRunning: Boolean = run
}
trait Emotion { def isEmoting: Boolean = true }
trait Speach { def isSpeaking: Boolean = true }
class Robot extends Runnable with Emotion with Speach

// Self Type
trait Greeting { def greeting: String }
trait Hello extends Greeting { override def greeting = "hello" }
trait Goodbye extends Greeting { override def greeting = "goodbye" }
class Speaker {
  self: Greeting =>
  def greet: String = greeting
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

class TypesTest extends FunSuite {
  test("variance") {
    val covariant: Covariant[Parent] = new Covariant[Child](new Child())
    val contravariant: Contravariant[Child, Parent] = new Contravariant[Child, Parent](new Parent())
    val invariant: Invariant[Child] = new Invariant[Child](new Child())
    assert(covariant.relative.isInstanceOf[Child])
    assert(contravariant.relative.isInstanceOf[Parent])
    assert(invariant.relative.isInstanceOf[Child])
  }

  test("contravariant in, covariant out") {
    val filter = new PositiveFilter[Int, Boolean] {
      override def isPositive(n: Int): Boolean = n > 0
    }
    val numbers = List(-3, -2, -1, 0, 1, 2, 3)
    val positives: List[Int] = numbers.filter(n => filter.isPositive(n))
    assert(positives == List(1, 2, 3))
  }

  test("bounds") {
    val upperBounds: Int = UpperBounds(3)
    assert(upperBounds == 3)
    val lowerBounds: Any = LowerBounds(3)
    assert(lowerBounds == 3)
  }

  test("compound types") {
    val robot = new Robot()
    assert(robot.isRunning)
    assert(robot.isEmoting)
    assert(robot.isSpeaking)
  }

  test("type alias") {
    type User = String
    type Age = Int
    val users:  Map[User, Age] =  Map("john" -> 21, "jane" -> 19)
    assert(users("john") == 21)
    assert(users("jane") == 19)
  }

  test("duck typing") {
    class Greeter { def greet = "Hi!" }
    def greet(greeter: { def greet: String } ): String = greeter.greet
    assert(greet(new Greeter) == "Hi!")
  }

  test("self type") {
    val hello = new Speaker() with Hello
    assert(hello.greet == "hello")
    val goodbye = new Speaker() with Goodbye
    assert(goodbye.greet == "goodbye")
  }

  test("path dependent types") {
    val first1 = new First()
    val path1 = new first1.Second()
    val first2 = new First()
    val path2 = new first2.Second()
    assert(path1 != path2)
  }

  test("type instances") {
    val minOrdering: Ordering[Int] = Ordering.fromLessThan[Int](_ < _)
    val maxOrdering: Ordering[Int] = Ordering.fromLessThan[Int](_ > _)
    assert(List(3, 4, 2).sorted(minOrdering) == List( 2, 3, 4))
    assert(List(3, 4, 2).sorted(maxOrdering) == List(4, 3, 2))
  }

  test("implicit type instance") {
    implicit val ordering: Ordering[String] = Ordering.fromLessThan[String](_ < _)
    assert(List("c", "b", "a").sorted == List("a", "b", "c"))
  }

  test("companion object with implicit") {
    assert(List(Rational(1, 2), Rational(3, 4), Rational(1, 3)).sorted == List(Rational(1, 3), Rational(1, 2), Rational(3, 4)))
  }
}