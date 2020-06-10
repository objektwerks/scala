package collection

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.ListMap
import scala.collection.{SortedMap, mutable}

class MapTest extends AnyFunSuite with Matchers {
  test("map") {
    val map = Map(1 -> 1, 2 -> 2)
    map(1) shouldEqual 1
    map(2) shouldEqual 2
    map.getOrElse(3, -1) shouldEqual -1
    map.contains(1) shouldBe true
    map shouldEqual Map(1 -> 1) + (2 -> 2)
    map shouldEqual Map(1 -> 1, 2 -> 2, 3 -> 3) - 3
    map shouldEqual Map(1 -> 1) ++ Map(2 -> 2)
    map shouldEqual Map(1 -> 1, 2 -> 2, 3 -> 3, 4 -> 4) -- List(3, 4)
    map.keySet shouldEqual Set(1, 2)
    map.values.toSet shouldEqual Set(1, 2)
    map.empty.isEmpty shouldBe true
  }

  test("list map") {
    val map = ListMap(3 -> 3, 2 -> 2, 1 -> 1)
    val list = map.keys.toIndexedSeq
    list(0) shouldEqual 3
    list(1) shouldEqual 2
    list(2) shouldEqual 1
  }

  test("sorted map") {
    val map = SortedMap(3 -> 3, 2 -> 2, 1 -> 1)
    val list = map.keys.toIndexedSeq
    list(0) shouldEqual 1
    list(1) shouldEqual 2
    list(2) shouldEqual 3
  }

  test("mutable map") {
    val map = mutable.Map(1 -> 1, 2 -> 2)
    (map += 3 -> 3) shouldEqual Map(1 -> 1, 2 -> 2, 3 -> 3)
    (map -= 3) shouldEqual Map(1 -> 1, 2 -> 2)
    (map -= 2) shouldEqual Map(1 -> 1)
    (map -= 1) shouldEqual Map()
    (map ++= List(1 -> 1, 2 -> 2)) shouldEqual Map(1 -> 1, 2 -> 2)
    (map --= List(1, 2)) shouldEqual Map()
  }
}