package types

import org.scalatest.FunSuite

// Product | Has-A-And Pattern
trait C
trait B
trait A { // A has-a B and C
def b: B
  def c: C
}
case class D(b: B, c: C)  // D has-a B and C

// Product | Has-A-Or Pattern
sealed trait P
trait O {
  def p: P  // O has-a Q or R
}
final case class Q(q: Any) extends P
final case class R(r: Any) extends P

final case class H(b: B) extends P  // O is-a H or I, and H has-a B and I has-a C
final case class I(c: C) extends P

// Sum | Is-A-Or Pattern
sealed trait Z  // Z is-a X or Y
final case class X(x: Any) extends Z
final case class Y(y: Any) extends Z

// Sum | Is-A-And Pattern
trait L
trait M
trait N extends L with M  // N is-a L and M

// Variance
trait Relative
class Parent extends Relative
class Child extends Parent
class Covariant[+R](val relative: R)
class Contravariant[-R, +S](val relative: S)
class Invariant[R](val relative: R)
trait PositiveFilter[-A, +B] { def isPositive(n: Int): Boolean }

// Bounds
object UpperBounds { def apply[N <: AnyVal](n: N): N = identity(n) }
object LowerBounds { def apply[A >: AnyVal](a: A): A = identity(a) }

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
    assert(users.get("john").get == 21)
    assert(users.get("jane").get == 19)
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
}