package types

import org.scalatest.FunSuite

// Variance
trait Relative
class Parent extends Relative
class Child extends Parent
class Covariant[+R](val relative: R)
class Contravariant[-R, +S](val relative: S)
class Invariant[R](val relative: R)

// Compound Types
trait Speach { def speak: String = "arrgh" }
trait Emotion { def emotion: String = "happy" }
trait Init { def init: Boolean = true }
trait Run { def run: Boolean = true }
class Bootable extends Init with Run {
  def boot: Boolean = init; run
}
class Robot extends Bootable with Speach with Emotion

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

  test("compound types") {
    val robot = new Robot()
    val booted = robot.boot
    assert(booted)
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