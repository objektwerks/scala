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
}