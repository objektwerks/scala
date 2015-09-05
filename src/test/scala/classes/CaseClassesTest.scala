package classes

import org.scalatest.FunSuite

trait Animal {
  def sound: String = ""
}

case class Tiger(speach: String) extends Animal {
  override def sound: String = speach
}

case class Shark(speach: String) extends Animal {
  override def sound: String = speach
}

case class Bear(speach: String) extends Animal {
  override def sound: String = speach
}

case object ZooKeeper {
  def openCages: Set[Animal] = Set(new Tiger("prrrr"), new Shark("woosh"), new Bear("grrrr"))
}

case class Meter(value: Double) extends AnyVal {
  def toFeet: Foot = Foot(value * 0.3048)
}

case class Foot(value: Double) extends AnyVal {
  def toMeter: Meter = Meter(value / 0.3048)
}

class CaseClassesTest extends FunSuite {
  test("case classes") {
    val animals = ZooKeeper.openCages
    for(animal <- animals) {
      assert(animal.sound.nonEmpty)
      animal match {
        case Tiger(s) => assert(s == "prrrr")
        case Shark(s) => assert(s == "woosh")
        case Bear(s) => assert(s == "grrrr")
      }
    }
  }

  test("equality") {
    val t1 = Tiger("prrrr")
    val t2 = Tiger("prrrr")
    val t3 = Tiger("meow")
    assert(t1 == t2)
    assert(t1 != t3)
    assert(t1.hashCode == t2.hashCode)
    assert(t1.hashCode != t3.hashCode)
    assert(Tiger.unapply(t1).get == "prrrr")
    assert(t1 == Tiger.apply(t1.sound))
  }

  test("value classes") {
    assert(Meter(3.0).toFeet == Foot(0.9144000000000001))
    assert(Foot(3.0).toMeter == Meter(9.84251968503937))
  }
}