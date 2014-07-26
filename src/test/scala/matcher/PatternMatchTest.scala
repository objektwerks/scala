package matcher

import org.scalatest.FunSuite

class PatternMatchTest extends FunSuite {
  test("any match") {
    def isTrue(a: Any) = a match {
      case 0 | "" => false
      case _ => true
    }
    assert(isTrue(1))
    assert(!isTrue(0))
  }

  test("string match") {
    def isEqual(s: Int): String = s match {
      case 1 => "one"
      case 2 => "two"
      case _ => "many"
    }
    assert(isEqual(1) == "one")
    assert(isEqual(3) == "many")
  }

  test("case class match") {
    case class Person(name: String)
    def isPerson(p: Person): String = p match {
      case Person("John") => "Mr. " + p.name
      case Person("Jane") => "Ms. " + p.name
      case _ => "Mr. Nobody"
    }
    assert(isPerson(Person("John")) == "Mr. John")
    assert(isPerson(Person("Jake")) == "Mr. Nobody")
  }
}