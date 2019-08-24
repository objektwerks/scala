package pattern

import org.scalatest.{FunSuite, Matchers}

import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer
import scala.util.matching.Regex

class MatchTest extends FunSuite with Matchers {
  test("variable match") {
    case class Order(product: String, quantity: Int)
    def byVariable(order: Order): (String, Int) = order match {
      case Order(p, q) => (p, q)
    }
    val (product, quanity) = byVariable(Order("beer", 6))
    product shouldEqual "beer"
    quanity shouldEqual 6
  }

  test("type match") {
    def byType(t: Any): String = t match {
      case i:Int => s"integer: $i"
      case d:Double => s"double: $d"
      case s:String => s"string: $s"
    }
    byType(1) shouldEqual "integer: 1"
    byType(1.0) shouldEqual "double: 1.0"
    byType("10") shouldEqual "string: 10"
  }

  test("tuple match") {
    def byTuple(t: (Int, Int)): Int = t match {
      case (a, b) => a + b
    }
    byTuple((1, 2)) shouldEqual 3
  }

  test("or match") {
    def isTrue(a: Any) = a match {
      case 0 | "" => false
      case _ => true
    }
    isTrue(1) shouldBe true
    !isTrue(0) shouldBe true
    !isTrue("") shouldBe true
  }

  test("case class match") {
    case class Person(name: String)
    def byPerson(p: Person): String = p match {
      case Person("John") => "Mr. " + p.name
      case Person("Jane") => "Ms. " + p.name
      case Person(name) => s"Mr. $name"
    }
    byPerson(Person("John")) shouldEqual "Mr. John"
    byPerson(Person("Jane")) shouldEqual "Ms. Jane"
    byPerson(Person("Jake")) shouldEqual "Mr. Jake"
  }

  test("list match") {
    @tailrec
    def sum(numbers: List[Int], acc: Int = 0): Int = numbers match {
      case Nil => acc
      case head :: tail => sum(tail, acc + head)
    }
    sum(Nil) shouldEqual 0
    sum(List(1, 2, 3)) shouldEqual 6
  }

  test("guarded match") {
    val buffer = ArrayBuffer[String]()
    1 until 100 foreach {
      case i if i % 3 == 0 && i % 5 == 0 => buffer += s"$i -> m3 & m5"
      case i if i % 3 == 0 => buffer += s"$i -> m3"
      case i if i % 5 == 0 => buffer += s"$i -> m5"
      case i => buffer += i.toString
    }
    buffer.size shouldEqual 99
  }

  test("alias match") {
    case class Stock(symbol: String, price: Double)
    def isPriceHigher(today: Stock, yesterday: Stock): Boolean = today match {
      case t @ Stock(_, _) if t.symbol == yesterday.symbol => t.price > yesterday.price
    }
    val yesterday = Stock("XYZ", 1.11)
    val today = Stock("XYZ", 3.33)
    isPriceHigher(today, yesterday) shouldBe true
  }

  test("regex match") {
    val ipAddress = new Regex("""(\d+)\.(\d+)\.(\d+)\.(\d+)""")
    def byRegex(address: String): (Int, Int, Int, Int) = address match {
      case ipAddress(a, b, c, d) => (a.toInt, b.toInt, c.toInt, d.toInt)
    }
    (10, 10, 0, 1) shouldEqual byRegex("10.10.0.1")
  }
}