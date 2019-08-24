package collection

import org.scalatest.{FunSuite, Matchers}

case class CityStateZip(city: String, state: String, zip: Int) {
  def tupled: (String, String, Int) = (city, state, zip)
}

class TupleTest extends FunSuite with Matchers {
  test("tupled") {
    val (city, state, zip) = CityStateZip("placida", "florida", 33946).tupled
    city shouldEqual "placida"
    state shouldEqual "florida"
    zip shouldEqual 33946
  }

  test("tuple") {
    val (first, last, age) = ("fred", "flintstone", 99)
    first shouldEqual "fred"
    last shouldEqual "flintstone"
    age shouldEqual 99
  }
}