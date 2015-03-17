package types

import org.scalatest.FunSuite

class TypesTest extends FunSuite {
  test("covariance vs contravariance") {
    // GrandParent < Parent < Child
    Variance.covariance(new CovariantBox[Child])
    // Type mismatch, expected Parent. Variance.covariance(new CovariantBox[GrandParent])
    Variance.contravariance(new ContraviantBox[GrandParent])
    // Type mismatch, expected Parent. Variance.contravariance(new ContraviantBox[Child])
  }

  test("covariance") {
    val cat: Animal = new Cat("persia")
    val catTrainer: Trainer[Animal] = new Trainer(cat)
    assert(catTrainer.id == cat)
    assert(catTrainer.speak == cat.speak)

    val dog: Animal = new Dog("spike")
    val dogTrainer: Trainer[Animal] = new Trainer(dog)
    assert(dogTrainer.id == dog)
    assert(dogTrainer.speak == dog.speak)
  }

  test("contravariance") {
    val cake: Cake = new Cake("chocolate")
    val cakeBaker: Baker[Dessert] = new Baker(cake)
    assert(cakeBaker.id == cake)
    assert(cakeBaker.make == cake.bake)

    val cupCake: CupCake = new CupCake("vanila")
    val cupCakeBaker: Baker[Dessert] = new Baker(cupCake)
    assert(cupCakeBaker.id == cupCake)
    assert(cupCakeBaker.make == cupCake.bake)
  }

  test("invariance") {
    val football: Sport = new Football("bucs")
    val footballReferee: Referee[Sport] = new Referee(football)
    assert(footballReferee.id == football)
    assert(footballReferee.play == football.play)

    val soccer: Football = new Soccer("manchester united")
    val soccerReferee: Referee[Football] = new Referee(soccer)
    assert(soccerReferee.id == soccer)
    assert(soccerReferee.play == soccer.play)
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