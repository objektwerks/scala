package types

import org.scalatest.FunSuite

class TypesTest extends FunSuite {
  test("covariance") {
    abstract class Animal
    class Dog extends Animal { override def toString = "wolf wolf" }
    class Bird extends Dog { override def toString = "chirp chirp" }
    class Trainer[+A] () {
      def id[B >: A] (b: B): B = identity(b)
      def speak[B >: A] (b: B): String = b.toString
    }
    val trainer = new Trainer
    val dog: Dog = new Dog
    val bird: Bird = new Bird
    val animalDog: Animal = new Dog
    val animalBird: Animal = new Bird
    assert(trainer.speak(dog) == dog.toString)
    assert(trainer.speak(bird) == bird.toString)
    assert(trainer.speak(animalDog) == animalDog.toString)
    assert(trainer.speak(animalBird) == animalBird.toString)
    assert(trainer.id(dog) == dog)
    assert(trainer.id(bird) == bird)
    assert(trainer.id(animalDog) == animalDog)
    assert(trainer.id(animalBird) == animalBird)
  }

  test("contravariance") {
    abstract class Dessert
    class Cake extends Dessert { override def toString = "chocolate cake" }
    class Pie extends Dessert { override def toString = "key west lime pie" }
    class Baker[-A] () {
      def id[B <: A] (b: B): B = identity(b)
      def bake[B <: A] (b: B): String = b.toString
    }
    val baker = new Baker
    val cake: Cake = new Cake
    val pie: Pie = new Pie
    val dessertCake: Dessert = new Cake
    val dessertPie: Dessert = new Pie
    assert(baker.bake(cake) == cake.toString)
    assert(baker.bake(pie) == pie.toString)
    assert(baker.bake(dessertCake) == dessertCake.toString)
    assert(baker.bake(dessertPie) == dessertPie.toString)
    assert(baker.id(cake) == cake)
    assert(baker.id(pie) == pie)
    assert(baker.id(dessertCake) == dessertCake)
    assert(baker.id(dessertPie) == dessertPie)
  }

  test("invariance") {
    abstract class Company
    class National extends Company { override def toString = "subway" }
    class Multinational extends Company { override def toString = "cisco" }
    class Owner[A] () {
      def id[B] (b: B): B = identity(b)
      def name[B] (b: B): String = b.toString
    }
    val owner = new Owner
    val national: National = new National
    val multinational: Multinational = new Multinational
    val companyNational: Company = new Multinational
    val companyMultinational = new National
    assert(owner.name(national) == national.toString)
    assert(owner.name(multinational) == multinational.toString)
    assert(owner.name(companyNational) == companyNational.toString)
    assert(owner.name(companyMultinational) == companyMultinational.toString)
    assert(owner.id(national) == national)
    assert(owner.id(multinational) == multinational)
    assert(owner.id(companyNational) == companyNational)
    assert(owner.id(companyMultinational) == companyMultinational)
  }

  test("type alias") {
    type User = String
    type Age = Int
    val users:  Map[User, Age] =  Map("john" -> 21, "jane" -> 19)
    assert(users.get("john").get == 21)
    assert(users.get("jane").get == 19)
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

  test("singleton types") {
    case class Addresses(from: String, to: String, cc: String = "")
    case class Message(subject: String, text: String)
    case class Email(addresses: Addresses, message: Message)
    class EmailBuilder() {
      private var email: Option[Email] = None

      def build(addresses: Addresses, message: Message): this.type = {
        email = Some(Email(addresses, message))
        this
      }

      def send(): Option[Email] = {
        println(s"Simulating email send...$email")
        email
      }
    }
    val builder = new EmailBuilder()
    val email = builder.build(Addresses("me", "you", "them"), Message("us", "Meet as the pub for beer!")).send()
    assert(email != None)
  }

  test("generic function") {
    def selectMiddleItem[A](a: Array[A]): A = a(a.length / 2)
    assert(selectMiddleItem(Array("a", "b", "c")) == "b")
  }
}