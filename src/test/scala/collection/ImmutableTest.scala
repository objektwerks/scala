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
  }

  test("vector") {
    val vector = Vector(1, 2, 3)
    assert(vector == 1 +: Vector(2, 3))
    assert(vector == Vector(1, 2) :+ 3)
    assert(vector == Vector(1) ++ Vector(2, 3))
    assert(vector == Vector(1) ++: Vector(2, 3))
    assert(vector.head == 1)
    assert(vector.tail == Vector(2, 3))
    assert(vector.last == 3)
    assert(vector.sum == 6)
    assert(vector.filter(_ > 1) == Vector(2, 3))
    assert(vector.map(_ * 2) == Vector(2, 4, 6))
    assert((vector :+ 4) == Vector(1, 2, 3, 4))
    assert((vector drop 1) == Vector(2, 3))
    assert(vector.dropWhile(_ < 2) == Vector(2, 3))
    assert(vector.dropRight(1) == Vector(1, 2))
    assert((vector take 2) == Vector(1, 2))
    assert(vector.takeWhile(_ < 3) == Vector(1, 2))
    assert(vector.takeRight(1) == Vector(3))
    assert(vector.slice(0, 2) == Vector(1, 2))
    assert(vector.mkString(", ") == "1, 2, 3")
  }

  test("tuple") {
    val (first, last) = ("john", "doe")
    assert(first == "john" && last == "doe")

    val (city, state) = "Tampa" -> "Florida"
    assert(city == "Tampa" && state == "Florida")
  }

  test("view") {
    val result = (1 to 10000000).view.map(_ % 10).filter(_ > 5).sum
    assert(result == 30000000)
  }

  test("view non") {
    val result = (1 to 10000000).map(_ % 10).filter(_ > 5).sum
    assert(result == 30000000)
  }

  test("iterator") {
    val result = (1 to 10000000).iterator.map(_ % 10).filter(_ > 5).sum
    assert(result == 30000000)
  }

  test("stream") {
    val numberOfEvens = (1 to 100).toStream.count(_ % 2 == 0)
    assert(numberOfEvens == 50)
  }
}