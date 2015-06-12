package types

// Variance
class GrandParent
class Parent extends GrandParent
class Child extends Parent

class CovariantBox[+A]
class ContraviantBox[-A]

object Variance {
  def covariance(x: CovariantBox[Parent]): CovariantBox[Parent] = identity(x)
  def contravariance(x: ContraviantBox[Parent]): ContraviantBox[Parent] = identity(x)
}

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
trait Greeter {
  val greeting = "test"
}
trait Prompter {
  self: Greeter =>

  def greet: String = greeting
}