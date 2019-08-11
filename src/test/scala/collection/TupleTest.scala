package collection

import org.scalatest.{FunSuite, Matchers}

class TupleTest extends FunSuite with Matchers {
  test("tuple") {
    val cityStateZip = ("placida", "florida", 33946)
    assert(cityStateZip._1 == "placida" && cityStateZip._2 == "florida"  && cityStateZip._3 == 33946)
    val (first, last, age) = ("fred", "flintstone", 99)
    assert(first == "fred" && last == "flintstone" && age == 99)
  }
}