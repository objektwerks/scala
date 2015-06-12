package classes

import org.scalatest.FunSuite

case class Meter(value: Double) extends AnyVal {
  def toFeet: Foot = Foot(value * 0.3048)
}

case class Foot(value: Double) extends AnyVal {
  def toMeter: Meter = Meter(value / 0.3048)
}

class ClassesTest extends FunSuite {
  abstract class Animal {
    def sound: String
  }

  class Tiger extends Animal {
    def sound: String = "prrrr"
  }

  class Shark extends Animal {
    def sound: String = "woosh"
  }

  class Bear extends Animal {
    def sound: String = "grrrr"
  }

  object ZooKeeper {
    def openCages: Set[Animal] = Set(new Tiger, new Shark, new Bear)
  }

  test("classes") {
    val animals = ZooKeeper.openCages
    for (animal <- animals) {
      assert(animal.sound.length > 0)
    }
  }

  test("value classes") {
    assert(Meter(3.0).toFeet == Foot(0.9144000000000001))
    assert(Foot(3.0).toMeter == Meter(9.84251968503937))
  }
}