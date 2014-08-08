package classes

import org.scalatest.FunSuite

class ClassesTest extends FunSuite {
  abstract class Animal {
    def sound(): String
  }

  class Tiger extends Animal {
    def sound(): String = "prrrr"
  }

  class Shark extends Animal {
    def sound(): String = "woosh"
  }

  class Bear extends Animal {
    def sound(): String = "grrrr"
  }

  object ZooKeeper {
    def openCages(): Set[Animal] = Set(new Tiger, new Shark, new Bear)
  }

  test("animals") {
    val animals = ZooKeeper.openCages()
    for (animal <- animals) {
      assert(animal.sound().length > 0)
    }
  }
}