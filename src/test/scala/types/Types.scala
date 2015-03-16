package types

// Covariant
abstract class Animal
class Dog extends Animal { override def toString = "wolf wolf" }
class Trainer[+A] () {
  def id[B >: A] (b: B): B = identity(b)
  def speak[B >: A] (b: B): String = b.toString
}

// Contravariant
abstract class Dessert
class Cake extends Dessert { override def toString = "chocolate cake" }
class Baker[-A] () {
  def id[B <: A] (b: B): B = identity(b)
  def bake[B <: A] (b: B): String = b.toString
}

// Invariant
abstract class Team
class Football extends Team { override def toString = "bucs" }
class Owner[A] () {
  def id[B] (b: B): B = identity(b)
  def play[B] (b: B): String = b.toString
}