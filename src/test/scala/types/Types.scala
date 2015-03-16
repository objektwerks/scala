package types

// Covariant
sealed abstract class Animal
class Dog extends Animal { override def toString = "wolf wolf" }
class Trainer[+A] () {
  def id[B >: A] (b: B): B = identity(b)
  def speak[B >: A] (b: B): String = b.toString
}

// Contravariant
sealed abstract class Dessert
class Cake extends Dessert { override def toString = "cake" }
class CupCake extends Cake { override def toString = "cup cake" }
class Baker[-A] () {
  def id[B <: A] (b: B): B = identity(b)
  def bake[B <: A] (b: B): String = b.toString
}

// Invariant
sealed abstract class Team
class Football extends Team { override def toString = "bucs" }
class Owner[A] () {
  def id[B] (b: B): B = identity(b)
  def play[B] (b: B): String = b.toString
}