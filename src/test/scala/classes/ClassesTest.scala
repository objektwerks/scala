package classes

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

sealed abstract class Car {
  def drive: String = "driving"
  def sound: String
}
final class Porsche extends Car {
  override def sound: String = "prrrr"
}
final class Corvette extends Car {
  override def sound: String = "woosh" 
}
final class Maserati extends Car { 
  override def sound: String = "grrrr" 
}
object Owner { 
  def startEngines: Set[Car] = Set(new Porsche, new Corvette, new Maserati) 
}

class Human(val first: String, val last: String, val initial: String) {
  def this(first: String, last: String) = {
    this(first, last, "")
  }
}

class Square { 
  def apply(n: Int): Int = n * n 
}

object Cube { 
  def apply(n: Int): Int = n * n * n 
}

class Timestamp(val seconds: Int)
object Timestamp {
  def apply(hours: Int, minutes: Int, seconds: Int): Timestamp = {
    new Timestamp( (hours * 60 * 60) + (minutes * 60) + seconds )
  }
}

class ClassesTest extends AnyFunSuite with Matchers {
  test("classes with inheritence") {
    val cars = Owner.startEngines
    for (car <- cars) {
      car.sound.nonEmpty shouldBe true
      car match {
        case porsche: Porsche =>
          porsche.drive shouldEqual "driving"
          porsche.sound shouldEqual "prrrr"
        case corvette: Corvette =>
          corvette.drive shouldEqual "driving"
          corvette.sound shouldEqual "woosh"
        case maserati: Maserati =>
          maserati.drive shouldEqual "driving"
          maserati.sound shouldEqual "grrrr"
      }
    }
  }

  test("constructors") {
    val primary = new Human("fred", "flintstone", "r")
    val secondary = new Human("barney", "rebel")
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