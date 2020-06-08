package classes

import org.scalatest.{FunSuite, Matchers}

abstract class Car { def drive: String = "driving"; def sound: String }
class Porsche extends Car { override def sound: String = "prrrr" }
class Corvette extends Car { override def sound: String = "woosh" }
class Maserati extends Car { override def sound: String = "grrrr" }
object Owner { def startEngines: Set[Car] = Set(new Porsche, new Corvette, new Maserati) }

class Name(val first: String, val last: String, val initial: String) {
  def this(first: String, last: String) = {
    this(first, last, "")
  }
}

class Square { def apply(n: Int): Int = n * n }

object Cube { def apply(n: Int): Int = n * n * n }

class Timestamp(val seconds: Int)
object Timestamp {
  def apply(hours: Int, minutes: Int, seconds: Int): Timestamp = {
    new Timestamp( (hours * 60 * 60) + (minutes * 60) + seconds )
  }
}

class ClassesTest extends FunSuite with Matchers {
  test("classes with inheritence") {
    val cars = Owner.startEngines
    for (car <- cars) {
      car.sound.nonEmpty shouldBe true
      car match {
        case p: Porsche =>
          p.drive shouldEqual "driving"
          p.sound shouldEqual "prrrr"
        case c: Corvette => c.drive shouldEqual "driving"
          c.sound shouldEqual "woosh"
        case m: Maserati =>
          m.drive shouldEqual "driving"
          m.sound shouldEqual "grrrr"
      }
    }
  }

  test("constructors") {
    val primary = new Name("fred", "flintstone", "r")
    val secondary = new Name("barney", "rebel")
    primary.initial.nonEmpty shouldBe true
    secondary.initial.isEmpty shouldBe true
  }

  test("class apply") {
    val square = new Square()
    square(2) shouldEqual 4
    square.apply(3) shouldEqual 9
  }

  test("object apply") {
    Cube(2) shouldEqual 8
    Cube.apply(3) shouldEqual 27
  }

  test("companion object") {
    Timestamp(1, 1, 1).seconds shouldEqual 3661
  }
}