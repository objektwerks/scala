package classes

import org.scalatest.FunSuite

class CaseClassesTest extends FunSuite {
  trait Animal {
    def sound(): String
  }

  case class Tiger(speach: String) extends Animal {
    def sound(): String = speach
  }

  case class Shark(speach: String) extends Animal {
    def sound(): String = speach
  }

  case class Bear(speach: String) extends Animal {
    def sound(): String = speach
  }

  object ZooKeeper {
    def openCages(): Set[Animal] = Set(new Tiger("prrrr"), new Shark("woosh"), new Bear("grrrr"))
  }

  test("animals") {
    val animals = ZooKeeper.openCages()
    for(animal <- animals) {
      assert(animal.sound().length > 0)
      animal match {
        case Tiger(s) => println(s"Tiger: $s")
        case Shark(s) => println(s"Shark: $s")
        case Bear(s) => println(s"Bear: $s")
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
    assert(t1 == Tiger.apply(t1.sound()))
  }
}