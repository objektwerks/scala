package types

import org.scalatest.FunSuite

// Trait
trait Speach { def speak(): String = "arrgh" }
trait Emotion { def emotion(): String = "happy" }
trait On { def on(): Int = 1 }
trait Off { def off(): Int = -1 }

// Class with Compound Types and Traits
class Robot(power: On with Off) extends Speach with Emotion

// Covariant
sealed abstract class Animal(name: String) { def speak: String }
class Cat(name: String) extends Animal(name) { override def speak = "meow meow" }
class Dog(name: String) extends Animal(name) { override def speak = "wolf wolf" }
class Trainer[+A] (animal: Animal) {
  def id[T] = identity(animal)
  def speak[T <: Animal](): String = animal.speak
}

// Contravariant
sealed abstract class Dessert(name: String) { def bake: String }
class Cake(name: String) extends Dessert(name) { override def bake = "mix, bake and frost"}
class CupCake(name: String) extends Cake(name) { override def bake = "mix, bake, frost and package"}
class Baker[-A] (cake: Cake) {
  def id[T] = identity(cake)
  def make[T >: CupCake](): String = cake.bake
}

// Invariant
sealed abstract class Sport(name: String) { def play: String }
class Football(name: String) extends Sport(name) { override def play = "go bucs go!" }
class Soccer(name: String) extends Football(name) { override def play = "go manchester united go!" }
class Referee[A] (sport: Sport) {
  def id[T] = identity(sport)
  def play[T](): String = sport.play
}

// Self Type
trait Greeting { def greeting: String }
trait Hello extends Greeting { override def greeting = "hello"}
trait Goodbye extends Greeting { override def greeting = "goodbye"}
class Speaker {
  self: Greeting =>
  def greet: String = greeting
}

// Path Dependent Types
class First {
  class Second
}

class TypesTest extends FunSuite {
  test("covariance") {
    val cat: Animal = new Cat("persia")
    val catTrainer: Trainer[Animal] = new Trainer(cat)
    assert(catTrainer.id == cat)
    assert(catTrainer.speak == cat.speak)

    val dog: Animal = new Dog("spike")
    val dogTrainer: Trainer[Animal] = new Trainer(dog)
    assert(dogTrainer.id == dog)
    assert(dogTrainer.speak == dog.speak)
  }

  test("contravariance") {
    val cake: Cake = new Cake("chocolate")
    val cakeBaker: Baker[Dessert] = new Baker(cake)
    assert(cakeBaker.id == cake)
    assert(cakeBaker.make == cake.bake)

    val cupCake: CupCake = new CupCake("vanila")
    val cupCakeBaker: Baker[Dessert] = new Baker(cupCake)
    assert(cupCakeBaker.id == cupCake)
    assert(cupCakeBaker.make == cupCake.bake)
  }

  test("invariance") {
    val football: Sport = new Football("bucs")
    val footballReferee: Referee[Sport] = new Referee(football)
    assert(footballReferee.id == football)
    assert(footballReferee.play == football.play)

    val soccer: Football = new Soccer("manchester united")
    val soccerReferee: Referee[Football] = new Referee(soccer)
    assert(soccerReferee.id == soccer)
    assert(soccerReferee.play == soccer.play)
  }

  test("type alias") {
    type User = String
    type Age = Int
    val users:  Map[User, Age] =  Map("john" -> 21, "jane" -> 19)
    assert(users.get("john").get == 21)
    assert(users.get("jane").get == 19)
  }

  test("duck typing") {
    class Greeter { def greet = "Hi!" }
    def greet(greeter: {def greet: String}): String = greeter.greet
    assert(greet(new Greeter) == "Hi!")
  }

  test("self type") {
    val hello = new Speaker() with Hello
    assert(hello.greet == "hello")
    val goodbye = new Speaker() with Goodbye
    assert(goodbye.greet == "goodbye")
  }

  test("path dependent types") {
    val first1 = new First()
    val path1 = new first1.Second()
    val first2 = new First()
    val path2 = new first2.Second()
    assert(path1 != path2)
  }
}