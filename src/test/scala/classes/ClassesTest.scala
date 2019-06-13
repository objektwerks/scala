package classes

import org.scalatest.FunSuite

abstract class Car { def drive: String = "driving"; def sound: String }
class Porsche extends Car { override def sound: String = "prrrr" }
class Corvette extends Car { override def sound: String = "woosh" }
class Maserati extends Car { override def sound: String = "grrrr" }
object Owner { def startEngines: Set[Car] = Set(new Porsche, new Corvette, new Maserati) }

class Person(val first: String, val last: String, val initial: String) {
  def this(first: String, last: String) {
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

class ClassesTest extends FunSuite {
  test("classes with inheritence") {
    val cars = Owner.startEngines
    for (car <- cars) {
      assert(car.sound.nonEmpty)
      car match {
        case p: Porsche => assert(p.drive == "driving" && p.sound == "prrrr")
        case c: Corvette => assert(c.drive == "driving" && c.sound == "woosh")
        case m: Maserati => assert(m.drive == "driving" && m.sound == "grrrr")
      }
    }
  }

  test("constructors") {
    val primary = new Person("fred", "flintstone", "r")
    val secondary = new Person("barney", "rebel")
    assert(primary.initial.nonEmpty)
    assert(secondary.initial.isEmpty)
  }

  test("class apply") {
    val square = new Square()
    assert(square(2) == 4)
  }

  test("object apply") {
    assert(Cube(2) == 8)
  }

  test("companion object") {
    assert(Timestamp(1, 1, 1).seconds == 3661)
  }
}