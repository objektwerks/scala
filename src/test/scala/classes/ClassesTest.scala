package classes

import org.scalatest.FunSuite

abstract class Car {
  def sound: String = ""
}

class Porsche extends Car {
  override def sound: String = "prrrr"
}

class Corvette extends Car {
  override def sound: String = "woosh"
}

class Maserati extends Car {
  override def sound: String = "grrrr"
}

object Owner {
  def startEngines: Set[Car] = Set(new Porsche, new Corvette, new Maserati)
}

class ClassesTest extends FunSuite {
  test("classes") {
    val cars = Owner.startEngines
    for (car <- cars) {
      assert(car.sound.nonEmpty)
    }
  }
}