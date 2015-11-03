package pattern

import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer
import scala.util.matching.Regex

class MatchTest extends FunSuite {
  test("variable match") {
    case class Order(product: String, quantity: Int)
    def byVariable(order: Order): (String, Int) = order match {
      case Order(p, q) => (p, q)
    }
    val (product, quanity) = byVariable(Order("beer", 6))
    assert(product == "beer" && quanity == 6)
  }

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

  test("tuple match") {
    def byTuple(t: (Int, Int)): Int = t match {
      case (a, b) => a + b
    }
    assert(byTuple((1, 2)) == 3)
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

  test("case class match") {
    case class Person(name: String)
    def isPerson(p: Person): String = p match {
      case Person("John") => "Mr. " + p.name
      case Person("Jane") => "Ms. " + p.name
      case _ => "Mr. Nobody"
    }
    assert(isPerson(Person("John")) == "Mr. John")
    assert(isPerson(Person("Jane")) == "Ms. Jane")
    assert(isPerson(Person("Jake")) == "Mr. Nobody")
  }

  test("list match") {
    def byList(xs: List[Int]): Int = xs match {
      case Nil => 0
      case head :: tail => head + tail.sum
    }
    assert(byList(Nil) == 0)
    assert(byList(List(1, 2, 3)) == 6)
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

  test("guarded match") {
    val buffer = ArrayBuffer[String]()
    1 until 100 foreach {
      case i if i % 3 == 0 && i % 5 == 0 => buffer += s"$i -> m3 & m5"
      case i if i % 3 == 0 => buffer += s"$i -> m3"
      case i if i % 5 == 0 => buffer += s"$i -> m5"
      case i => buffer += i.toString
    }
    assert(buffer.size == 99)
  }

  test("alias match") {
    case class Stock(symbol: String, price: Double)
    def isPriceHigher(yesterday: Stock, today: Stock): Boolean = today match {
      case s @ Stock(_, price) if yesterday.symbol == today.symbol => s.price > yesterday.price
    }
    val yesterday = Stock("XYZ", 1.11)
    val today = Stock("XYZ", 3.33)
    assert(isPriceHigher(yesterday, today))
  }

  test("regex match") {
    val ipAddress = new Regex("""(\d+)\.(\d+)\.(\d+)\.(\d+)""")
    def byRegex(address: String): (Int, Int, Int, Int) = address match {
      case ipAddress(a, b, c, d) => (a.toInt, b.toInt, c.toInt, d.toInt)
    }
    assert((10, 10, 0, 1) == byRegex("10.10.0.1"))
  }
}