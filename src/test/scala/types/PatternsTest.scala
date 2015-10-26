package types

import org.scalatest.FunSuite

import scala.annotation.tailrec

// Product | Has-A-And Pattern
trait C
trait B
trait A { // A has-a B and C
def b: B
  def c: C
}
case class D(b: B, c: C)  // D has-a B and C

// Product | Has-A-Or Pattern
sealed trait P
final case class Q(q: Any) extends P
final case class R(r: Any) extends P
trait O {  // O has-a Q or R
def p: P
}
final case class H(b: B) extends P  // O is-a H or I, and H has-a B and I has-a C
final case class I(c: C) extends P

// Sum | Is-A-Or Pattern
sealed trait Z  // Z is-a X or Y
final case class X(x: Any) extends Z
final case class Y(y: Any) extends Z

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

// Recursive Data
sealed trait IntList
case object End extends IntList
case class Pair(head: Int, tail: IntList) extends IntList
object SumIntList {
  @tailrec
  final def sum(list: IntList, total: Int = 0): Int =
    list match {
      case End => total
      case Pair(head, tail) => sum(tail, total + head)
    }
}
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

  test("resursive data") {
    val list = Pair(1, Pair(2, Pair(3, End)))
    assert(SumIntList.sum(list) == 6)
    assert(SumIntList.sum(list.tail) == 5)
    assert(SumIntList.sum(End) == 0)
  }
}