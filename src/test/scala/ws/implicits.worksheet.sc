// Parameters
implicit val item = "beers"
def order(number: Int)(implicit item: String): String = {
  s"$number $item"
}
val beers = order(2)

// Conversions
implicit def intToString(i: Int): String = i.toString
val three: String = 3

// Extensions
object StringExtensions {
  implicit class Methods(val s: String) {
    def toJson = s"{$s}"
    def toXml = s"<$s>"
  }
}
import StringExtensions._
val json = "json".toJson
val xml = "xml".toXml

// Implicitly
trait Container[T]{ def content: T }
def view[T: Container] = implicitly[Container[T]].content

implicit object IntContainer extends Container[Int]{ def content = 123 }
implicit object StringContainer extends Container[String]{ def content = "abc" }

view[Int]
view[String]

// Ordering
case class Value(number: Int)
object Value {
  implicit class Ops(val value: Value) {
    def +(other: Value): Value = Value(value.number + other.number)
  }
  implicit def ordering: Ordering[Value] = Ordering.by(_.number)
}
import Value._
val values = List(3, 2, 1).map(n => Value(n))
values.foldLeft(Value(0))(_ + _)
values.sorted

// Types and more implicit extensions
sealed trait Animal
case class Cat(name: String) extends Animal
case class Dog(name: String) extends Animal

trait Speech[A] {
  def speak(a: A): String
}

object SpeechBehavior {
  implicit val catSpeech = new Speech[Cat] {
    def speak(cat: Cat): String = s"... meow, meow, says ${cat.name}!"
  }
  implicit val dogSpeech = new Speech[Dog] {
    def speak(dog: Dog): String = s"... ruff, ruff, says ${dog.name}!"
  }
}

object SpeechSyntax {
  implicit class SpeechOps[A](value: A) {
    def speak(implicit speech: Speech[A]): String = speech.speak(value)
  }
}

import SpeechBehavior._
import SpeechSyntax._

val cat = Cat("Jerry")
cat.speak

val dog = Dog("Bruiser")
dog.speak