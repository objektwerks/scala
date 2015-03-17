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
  def id = identity(animal)
  def speak: String = animal.speak
}

// Contravariant
sealed abstract class Dessert(name: String) { def bake: String }
class Cake(name: String) extends Dessert(name) { override def bake = "mix, bake and frost"}
class CupCake(name: String) extends Cake(name) { override def bake = "mix, bake, frost and package"}
class Baker[-A] (cake: Cake) {
  def id = identity(cake)
  def make: String = cake.bake
}

// Invariant
sealed abstract class Sport(name: String) { def play: String }
class Football(name: String) extends Sport(name) { override def play = "go bucs go!" }
class Soccer(name: String) extends Football(name) { override def play = "go manchester united go!" }
class Referee[A] (sport: Sport) {
  def id = identity(sport)
  def play: String = sport.play
}