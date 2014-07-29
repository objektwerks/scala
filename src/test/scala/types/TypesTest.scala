package types

import org.scalatest.FunSuite

class TypesTest extends FunSuite {
  test("generic function") {
    def getMiddle[A](a: Array[A]): A = a(a.length / 2)
    assert(getMiddle(Array("a", "b", "c")) == "b")
  }

  test("covariance") {
    trait Animal
    class Dinosaur(sound: String) extends Animal { override def toString = sound }
    class Bird(sound: String) extends Dinosaur(sound) { override def toString = sound }
    class Ping[+A] () {
      def test[B >: A] (b: B): String = b.toString
    }
    val ping = new Ping()
    val dinosaur: Dinosaur = new Dinosaur("screech")
    val bird: Bird = new Bird("chirp chirp")
    var animal: Animal = new Dinosaur("screech")
    animal = new Bird("chirp chirp")
    assert(ping.test(dinosaur) == dinosaur.toString)
    assert(ping.test(bird) == bird.toString)
    assert(ping.test(animal) == animal.toString)
    assert(ping.test(animal) == animal.toString)
  }

  test("contravariance") {
    class Ping[-A] () {

    }
  }

  test("invariance") {
    class Ping[A] () {

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