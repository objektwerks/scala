package classes

import org.scalatest.FunSuite

sealed trait Animal { def speak: String }
case class Tiger(speach: String) extends Animal { override def speak: String = speach }
case class Shark(speach: String) extends Animal { override def speak: String = speach }
case class Bear(speach: String) extends Animal { override def speak: String = speach }
case object ZooKeeper { def openCages: Set[Animal] = Set(Tiger("prrrr"), Shark("woosh"), Bear("grrrr")) }

case class Meter(value: Double) extends AnyVal { def toFeet: Foot = Foot(value * 0.3048) }
case class Foot(value: Double) extends AnyVal { def toMeter: Meter = Meter(value / 0.3048) }

class CaseClassesTest extends FunSuite {
  test("case classes") {
    val animals = ZooKeeper.openCages
    for(animal <- animals) {
      assert(animal.speak.nonEmpty)
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
    assert( (t1 eq t1) && (t2 eq t2) && (t3 eq t3) )
    assert(t1.equals(t2))
    assert(!t1.equals(t3))
    assert(t1 == t2)
    assert(t1 != t3)
    assert(t1.hashCode == t2.hashCode)
    assert(t1.hashCode != t3.hashCode)
  }

  test("copy") {
    val s1 = Shark("woosh")
    val s2 = s1.copy(speach = "arrrgh")
    assert(s1 == s1.copy())
    assert(s1 != s2)
  }

  test("to string") {
    val t1 = Tiger("prrrr")
    val t2 = Tiger("prrrr")
    assert(t1.toString == t2.toString)
  }

  test("apply unapply") {
    val t1 = Tiger("prrrr")
    assert(t1 == Tiger.apply(t1.speak))
    assert(Tiger.unapply(t1).get == "prrrr")
  }

  test("value classes") {
    assert(Meter(3.0).toFeet == Foot(0.9144000000000001))
    assert(Foot(3.0).toMeter == Meter(9.84251968503937))
  }
}