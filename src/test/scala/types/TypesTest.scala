package types

import org.scalatest.FunSuite

class TypesTest extends FunSuite {
  test("covariance") {
    val dog: Dog[AnyRef] = new Dog[String]
    val animal: Animal[AnyRef] = new Dog[String]
    val trainer: Trainer[AnyRef] = new Trainer[String]
    assert(trainer.speak(dog) == dog.toString)
    assert(trainer.speak(animal) == animal.toString)
    assert(trainer.id(dog) == dog)
    assert(trainer.id(animal) == animal)
  }

  test("contravariance") {
    val cake: Cake[String] = new Cake[AnyRef]
    val dessert: Dessert[String] = new Cake[AnyRef]
    val baker: Baker[AnyRef] = new Baker[AnyRef]
    assert(baker.bake(cake) == cake.toString)
    assert(baker.bake(dessert) == dessert.toString)
    assert(baker.id(cake) == cake)
    assert(baker.id(dessert) == dessert)
  }

  test("invariance") {
    val football: Football[String] = new Football[String]
    val sport: Sport[Nothing] = new Football[String]
    val referee: Referee[String] = new Referee[String]
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
}