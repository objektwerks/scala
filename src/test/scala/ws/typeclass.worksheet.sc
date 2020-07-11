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