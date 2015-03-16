package types

import org.scalatest.FunSuite

class TypesTest extends FunSuite {
  test("covariance") {
    val trainer = new Trainer
    val dog: Dog = new Dog
    val animal: Animal = new Dog
    assert(trainer.speak(dog) == dog.toString)
    assert(trainer.speak(animal) == animal.toString)
    assert(trainer.id(dog) == dog)
    assert(trainer.id(animal) == animal)
  }

  test("contravariance") {
    val baker = new Baker
    val cake: Cake = new Cake
    val dessert: Dessert = new Cake
    assert(baker.bake(cake) == cake.toString)
    assert(baker.bake(dessert) == dessert.toString)
    assert(baker.id(cake) == cake)
    assert(baker.id(dessert) == dessert)
  }

  test("invariance") {
    val owner = new Owner
    val football: Football = new Football
    val team: Team = new Football
    assert(owner.play(football) == football.toString)
    assert(owner.play(team) == team.toString)
    assert(owner.id(football) == football)
    assert(owner.id(team) == team)
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