package types

import org.scalatest.FunSuite

class TypesTest extends FunSuite {
  test("covariance") {
    val dog: Dog[String] = new Dog
    val animal: Animal[String] = new Dog
    val trainer: Trainer[String] = new Trainer
    assert(trainer.speak(dog) == dog.toString)
    assert(trainer.speak(animal) == animal.toString)
    assert(trainer.id(dog) == dog)
    assert(trainer.id(animal) == animal)
  }

  test("contravariance") {
    val cake: Cake[String] = new Cake
    val cupCake: CupCake[String] = new CupCake
    val dessertCake: Dessert[String] = new Cake
    val dessertCupCake: Dessert[String] = new CupCake
    val baker = new Baker
    assert(baker.bake(cake) == cake.toString)
    assert(baker.bake(cupCake) == cupCake.toString)
    assert(baker.bake(dessertCake) == dessertCake.toString)
    assert(baker.bake(dessertCupCake) == dessertCupCake.toString)
    assert(baker.id(cake) == cake)
    assert(baker.id(cupCake) == cupCake)
    assert(baker.id(dessertCake) == dessertCake)
    assert(baker.id(dessertCupCake) == dessertCupCake)
  }

  test("invariance") {
    val football: Football[String] = new Football
    val sport: Sport[Nothing] = new Football
    val referee: Referee[String] = new Referee
    assert(referee.play(football) == football.toString)
    assert(referee.play(sport) == sport.toString)
    assert(referee.id(football) == football)
    assert(referee.id(sport) == sport)
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
    assert(greet(new Greeter()) == "Hi!")
  }

  test("generic function") {
    def split[A](a: Array[A]): A = a(a.length / 2)
    assert(split(Array("a", "b", "c")) == "b")
  }
}