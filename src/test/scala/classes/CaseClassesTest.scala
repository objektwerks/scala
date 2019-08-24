package classes

import org.scalatest.{FunSuite, Matchers}

sealed trait Animal { def speak: String }
case class Tiger(speach: String) extends Animal { override def speak: String = speach }
case class Panther(speach: String) extends Animal { override def speak: String = speach }
case class Bear(speach: String) extends Animal { override def speak: String = speach }
case object ZooKeeper { def openCages: Set[Animal] = Set(Tiger("prrrr"), Panther("woosh"), Bear("grrrr")) }

case class Meter(value: Double) extends AnyVal { def toFeet: Foot = Foot(value * 0.3048) }
case class Foot(value: Double) extends AnyVal { def toMeter: Meter = Meter(value / 0.3048) }

class CaseClassesTest extends FunSuite with Matchers {
  test("case classes") {
    val animals = ZooKeeper.openCages
    for(animal <- animals) {
      animal.speak.nonEmpty shouldBe true
      animal match {
        case Tiger(speech) => speech shouldEqual "prrrr"
        case Panther(speech) => speech shouldEqual "woosh"
        case Bear(speech) => speech shouldEqual "grrrr"
      }
    }
  }

  test("equality") {
    val t1 = Tiger("roar")
    val t2 = Tiger("roar")
    val t3 = Tiger("prrrr")
    t1 shouldEqual t1
    t2 shouldEqual t2
    t3 shouldEqual t3
    t1 shouldEqual t2
    t1 should not equal t3
    t1.hashCode shouldEqual t2.hashCode
    t1.hashCode should not equal t3.hashCode
  }

  test("copy") {
    val p1 = Panther("prrrr")
    val p2 = p1.copy(speach = "arrrgh")
    p1 shouldEqual p1.copy()
    p1 should not equal p2
  }

  test("to string") {
    val t1 = Tiger("roar")
    val t2 = Tiger("roar")
    t1.toString shouldEqual t2.toString
  }

  test("apply unapply") {
    val t1 = Tiger("roar")
    t1 shouldEqual Tiger.apply(t1.speak)
    Tiger.unapply(t1).get shouldEqual "roar"
  }

  test("value classes") {
    Meter(3.0).toFeet shouldEqual Foot(0.9144000000000001)
    Foot(3.0).toMeter shouldEqual Meter(9.84251968503937)
  }
}