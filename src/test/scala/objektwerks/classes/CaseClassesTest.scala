package objektwerks.classes

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

sealed trait Animal { 
  def speak: String 
}
case class Tiger(speach: String) extends Animal { 
  override def speak: String = speach 
}
case class Panther(speach: String) extends Animal { 
  override def speak: String = speach 
}
case class Bear(speach: String) extends Animal { 
  override def speak: String = speach 
}
case object ZooKeeper { 
  def openCages: Set[Animal] = Set(Tiger("prrrr"), Panther("woosh"), Bear("grrrr")) 
}

case class Meter(value: Double) extends AnyVal { 
  def toFeet: Foot = Foot(value * 0.3048) 
}
case class Foot(value: Double) extends AnyVal { 
  def toMeter: Meter = Meter(value / 0.3048) 
}

class CaseClassesTest extends AnyFunSuite with Matchers {
  test("case objektwerks.classes") {
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
    val tiger1 = Tiger("roar")
    val tiger2 = Tiger("roar")
    val tiger3 = Tiger("prrrr")
    tiger1 shouldEqual tiger2
    tiger1 should not equal tiger3
    tiger2 should not equal tiger3
  }

  test("copy") {
    val panther1 = Panther("prrrr")
    val panther2 = panther1.copy(speach = "arrrgh")
    panther1 shouldEqual panther1.copy()
    panther1 should not equal panther2
  }

  test("toString") {
    val bear1 = Bear("grrrr")
    val bear2 = Bear("grrrr")
    bear1.toString shouldEqual bear2.toString
  }

  test("apply unapply") {
    val tiger1 = Tiger("roar")
    tiger1 shouldEqual Tiger.apply(tiger1.speak)
    Tiger.unapply(tiger1) shouldEqual Some("roar")
  }

  test("value class") {
    Meter(3.0).toFeet shouldEqual Foot(0.9144000000000001)
    Foot(3.0).toMeter shouldEqual Meter(9.84251968503937)
  }
}