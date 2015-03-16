package types

import org.scalatest.FunSuite

class TypesTest extends FunSuite {
  test("covariance") {
    val dog: Dog = new Dog
    val animal: Animal = new Dog
    val trainer = new Trainer
    assert(trainer.speak(dog) == dog.toString)
    assert(trainer.speak(animal) == animal.toString)
    assert(trainer.id(dog) == dog)
    assert(trainer.id(animal) == animal)
  }

  test("contravariance") {
    val cake: Cake = new Cake
    val cupCake: CupCake = new CupCake
    val dessertCake: Dessert = new Cake
    val dessertCupCake: Dessert = new CupCake
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
    val football: Football = new Football
    val team: Team = new Football
    val owner = new Owner
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