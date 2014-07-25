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

  test("animals") {
    val animals = Set(new Tiger, new Shark, new Bear)
    for (animal <- animals) {
      assert(animal.sound().length > 0)
    }
  }
}