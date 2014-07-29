package types

import org.scalatest.FunSuite

class TypesTest extends FunSuite {
  trait Animal { def speak(): String }
  case class Cat(sound: String) extends Animal { def speak(): String = sound }
  case class Dog(sound: String) extends Animal { def speak(): String = sound }

  test("generic function") {
    def getMiddle[A](a: Array[A]): A = a(a.length / 2)
    assert(getMiddle(Array("a", "b", "c")) == "b")
  }

  test("covariance") {
    class Container[+A] (val item: A)
    val cat = Cat("meow")
    val dog = Dog("woof")
    val cats = new Container(cat)
    val dogs = new Container(dog)
    println(cats.item.speak())
    println(dogs.item.speak())
  }

  test("contravariance") {
    class Container[A] (val item: A) {

    }
  }

  test("invariance") {
    class Container[A] (val item: A) {

    }
  }

  test("duck typing") {
    class Greeter {
      def greet = "Hi!"
    }
    def greet(greeter: {def greet: String}): String = {
      greeter.greet
    }
    assert(greet(new Greeter()) == "Hi!")
  }
}