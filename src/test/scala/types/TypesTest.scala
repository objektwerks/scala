package types

import org.scalatest.FunSuite

class TypesTest extends FunSuite {
  class Animal()
  class Dinosaur(sound: String) extends Animal { override def toString = sound }
  class Bird(sound: String) extends Dinosaur(sound) { override def toString = sound }

  test("generic function") {
    def getMiddle[A](a: Array[A]): A = a(a.length / 2)
    assert(getMiddle(Array("a", "b", "c")) == "b")
  }

  test("covariance") {
    class Ping[+A] () {
      def test[B >: A] (b: B): String = b.toString
    }
    val ping = new Ping()
    val dinosaur = new Dinosaur("screech")
    val bird = new Bird("chirp chirp")
    var animal = new Dinosaur("screech")
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