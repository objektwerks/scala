package types

import org.scalatest.FunSuite

// Product | Has-A-And Pattern
trait C
trait B
trait A { // A has-a B and C
  def b: B
  def c: C
}

// Product | Has-A-Or Pattern
sealed trait P
case class Q(q: Any) extends P
case class R(r: Any) extends P
trait O {  // O has-a Q or R
  def p: P
}

// Sum | Is-A-Or Pattern
sealed trait Z  // Z is-a X or Y
case class X(x: Any) extends Z
case class Y(y: Any) extends Z

// Sum | Is-A-And Pattern
trait L
trait M
trait N extends L with M  // N is-a L and M

// Polymorphism
sealed trait Food
case object Hamburger extends Food
case object Pizza extends Food

sealed trait SportsFan {
  def name: String
  def favorite: Food
}
case class BaseballFan(name: String) extends SportsFan {
  override def favorite: Food = Hamburger
}
case class FootballFan(name: String) extends SportsFan {
  override def favorite: Food = Pizza
}

// Pattern Matching
sealed trait Movie
case object Drama extends Movie
case object Action extends Movie

sealed trait MovieFan {
  def name: String
  def favorite: Movie = {
    this match {
      case Gal(_) => Drama
      case Guy(_) => Action
    }
  }
}
case class Guy(name: String) extends MovieFan
case class Gal(name: String) extends MovieFan

object MovieReporter {
  def favorite(fan: MovieFan): Movie = {
    fan match {
      case Gal(_) => Drama
      case Guy(_) => Action
    }
  }
}

// Recursive ADT
sealed trait IntList {
  def sum: Int = this match {
    case End => 0
    case Pair(head, tail) => head + tail.sum
  }
}
case object End extends IntList
case class Pair(head: Int, tail: IntList) extends IntList

class PatternsTest extends FunSuite {
  test("polymorphism") {
    val (baseballFan, footballFan): (SportsFan, SportsFan) = (BaseballFan("Fred"), FootballFan("Barney"))
    assert(baseballFan.favorite == Hamburger)
    assert(footballFan.favorite == Pizza)
  }

  test("pattern matching") {
    val (dramaFan, actionFan): (MovieFan, MovieFan) = (Gal("Betty"), Guy("Barney"))
    assert(dramaFan.favorite == Drama)
    assert(actionFan.favorite == Action)
    assert(MovieReporter.favorite(dramaFan) == Drama)
    assert(MovieReporter.favorite(actionFan) == Action)
  }

  test("resursive adt") {
    val list = Pair(1, Pair(2, Pair(3, End)))
    assert(list.sum == 6)
    assert(list.tail.sum == 5)
    assert(End.sum == 0)
  }
}