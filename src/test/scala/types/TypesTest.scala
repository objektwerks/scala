package types

import org.scalatest.FunSuite

class TypesTest extends FunSuite {
  test("generic function") {
    def getMiddle[A](a: Array[A]): A = a(a.length / 2)
    assert(getMiddle(Array("a", "b", "c")) == "b")
  }

  test("covariance") {
    class Animal
    class Dinosaur(sound: String) extends Animal { override def toString = sound }
    class Bird(sound: String) extends Dinosaur(sound) { override def toString = sound }
    class Ping[+A] () {
      def id[A] (a: A): A = identity(a)
      def test[B >: A] (b: B): String = b.toString
    }
    val ping = new Ping()
    val dinosaur: Dinosaur = new Dinosaur("screech")
    val bird: Bird = new Bird("chirp chirp")
    var animal: Animal = new Dinosaur("screech")
    animal = new Bird("chirp chirp")
    assert(ping.test(dinosaur) == dinosaur.toString)
    assert(ping.test(bird) == bird.toString)
    assert(ping.test(animal) == animal.toString)
    assert(ping.test(animal) == animal.toString)
    assert(ping.id(bird) == bird)
    assert(ping.id(dinosaur) == dinosaur)
    animal = new Animal
    assert(ping.id(animal) == animal)
  }

  test("contravariance") {
    class Food
    class Cake(style: String) extends Food { override def toString = style }
    class Chocolate(style: String) extends Cake(style) { override def toString = style }
    class Ping[-A] () {
      def id[A] (a: A): A = identity(a)
      def test[B <: A] (b: B): String = b.toString
    }
    val ping = new Ping()
    val cake:Cake = new Cake("cake")
    val chocolate: Chocolate = new Chocolate("chocolate")
    var food: Food = new Cake("cake")
    food = new Chocolate("chocolate")
    assert(ping.test(cake) == cake.toString)
    assert(ping.test(chocolate) == chocolate.toString)
    assert(ping.test(food) == food.toString)
    assert(ping.test(food) == food.toString)
    assert(ping.id(chocolate) == chocolate)
    assert(ping.id(cake) == cake)
    food = new Food
    assert(ping.id(food) == food)
  }

  test("invariance") {
    class Company
    class Multinational(business: String) extends Company { override def toString = business }
    class National(business: String) extends Multinational(business) { override def toString = business }
    class Ping[A] () {
      def id[A] (a: A): A = identity(a)
      def test[A] (a: A): String = a.toString
    }
    val ping = new Ping()
    val multinational: Multinational = new Multinational("IBM")
    val national: National = new National("Pier Imports")
    var company: Company = new Multinational("Cisco")
    company = new National("Marble Slab")
    assert(ping.test(multinational) == multinational.toString)
    assert(ping.test(national) == national.toString)
    assert(ping.test(company) == company.toString)
    assert(ping.test(company) == company.toString)
    assert(ping.id(national) == national)
    assert(ping.id(multinational) == multinational)
    company = new Company
    assert(ping.id(company) == company)
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
    case class Addresses(from: String, to: String, cc: String)
    case class Message(subject: String, text: String)
    case class Email(addresses: Option[Addresses], message: Option[Message])
    class EmailBuilder() {
      private var addresses: Option[Addresses] = None
      private var message: Option[Message] = None

      def addresses(from: String, to:String, cc: String = ""): this.type = {
        this.addresses = Some(Addresses(from, to, cc))
        this
      }

      def subject(subject: String, text: String): this.type = {
        this.message = Some(Message(subject, text))
        this
      }

      def build(): Option[Email] = {
        require(addresses != None)
        require(message != None)
        Some(Email(addresses, message))
      }
    }
    val builder = new EmailBuilder()
    val email = builder.addresses("me", "you", "them").subject("us", "Meet as the pub for beer!").build()
    assert(email != None)
  }
}