package types

// Covariant
sealed abstract class Animal[+A]
class Dog[+A] extends Animal { override def toString = "wolf wolf" }
class Trainer[+A] () {
  def id[B >: A] (b: B): B = identity(b)
  def speak[B >: A] (b: B): String = b.toString
}

// Contravariant
sealed abstract class Dessert[-A]
class Cake[-A] extends Dessert { override def toString = "cake" }
class CupCake[-A] extends Cake { override def toString = "cup cake" }
class Baker[-A] () {
  def id[B <: A] (b: B): B = identity(b)
  def bake[B <: A] (b: B): String = b.toString
}

// Invariant
sealed abstract class Sport[A]
class Football[A] extends Sport { override def toString = "bucs" }
class Referee[A] () {
  def id[B] (b: B): B = identity(b)
  def play[B] (b: B): String = b.toString
}