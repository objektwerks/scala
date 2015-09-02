package pattern

import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

class PatternMatchTest extends FunSuite {
  test("type match") {
    def byType(t: Any): String = t match {
      case i:Int => s"integer: $i"
      case d:Double => s"double: $d"
      case s:String => s"string: $s"
    }
    assert(byType(1) == "integer: 1")
    assert(byType(1.0) == "double: 1.0")
    assert(byType("10") == "string: 10")
  }

  test("or match") {
    def isTrue(a: Any) = a match {
      case 0 | "" => false
      case _ => true
    }
    assert(isTrue(1))
    assert(!isTrue(0))
    assert(!isTrue(""))
  }

  test("int to string match") {
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

  test("wild card case class match") {
    case class Order(number: Int, item: String)
    def order(o: Order): String = o match {
      case Order(_, "chicken soup") => o.number + " " + o.item
      case Order(_, _) => "we're out of that"
    }
    assert(order(Order(10, "chicken soup")) == "10 chicken soup")
    assert(order(Order(0, "")) == "we're out of that")
  }

  test("case modulo") {
    val buffer = ArrayBuffer[String]()
    1 until 100 foreach {
      case i if i % 3 == 0 && i % 5 == 0 => buffer += s"$i -> m3 & m5"
      case i if i % 3 == 0 => buffer += s"$i -> m3"
      case i if i % 5 == 0 => buffer += s"$i -> m5"
      case i => buffer += i.toString
    }
    assert(buffer.size == 99)
  }
}