package collection

import org.scalatest.FunSuite

class ImmutableTest extends FunSuite {
  test("set") {
    val set = Set(1, 2)
    assert(set == Set(1) ++ Set(2))
    assert(set == Set(1) + 2)
    assert(set == Set(1, 2, 3) - 3)
    assert(set.contains(1))
  }

  test("map") {
    val map = Map(1 -> 1, 2 -> 2)
    assert(map == Map(1 -> 1) ++ Map(2 -> 2))
    assert(map == Map(1 -> 1) + (2 -> 2))
    assert(map == Map(1 -> 1, 2 -> 2, 3 -> 3) - 3)
    assert(map.get(1).get == 1)
  }

  test("list") {
    val list = List(1, 2)
    assert(list == List(1) ::: List(2))
    assert(list == 1 :: List(2))
    assert(list == 1 :: 2 :: Nil)
    assert(list == List(1) ::: List(2))
    assert(list == 1 :: List(2))
    assert(list == 1 +: List(2))
    assert(list == List(1) :+ 2)
    assert(list == List(1) ++ List(2))
    assert(list == List(1) ++: List(2))
    assert(list.contains(1))
  }

  test("vector") {
    val vector = Vector(1, 2)
    assert(vector == 1 +: Vector(2))
    assert(vector == Vector(1) :+ 2)
    assert(vector == Vector(1) ++ Vector(2))
    assert(vector == Vector(1) ++: Vector(2))
    assert(vector.contains(1))
  }

  test("array") {
    val array = Array(1, 2)
    assert(array(0) == 1)
    assert(array.reverse === Array(2, 1))
    assert(array === Array(1) ++ Array(2))
    assert(array === 1 +: Array(2))
    assert(array === Array(1) :+ 2)
  }

  test("stream") {
    val numberOfEvens = (1 to 100).toStream.count(_ % 2 == 0)
    assert(numberOfEvens == 50)
  }

  test("tuple") {
    val (first, last) = ("fred", "flintstone")
    assert(first == "fred" && last == "flintstone")
    val (city, state) = "barney" -> "rebel"
    assert(city == "barney" && state == "rebel")
  }
}