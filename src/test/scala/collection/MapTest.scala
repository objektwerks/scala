package collection

import org.scalatest.{FunSuite, Matchers}

import scala.collection.immutable.ListMap
import scala.collection.{SortedMap, mutable}

class MapTest extends FunSuite with Matchers {
  test("map") {
    val map = Map(1 -> 1, 2 -> 2)
    assert(map(1) == 1)
    assert(map(2) == 2)
    assert(map.getOrElse(3, -1) == -1)
    assert(map.contains(1))
    assert(map == Map(1 -> 1) + (2 -> 2))
    assert(map == Map(1 -> 1, 2 -> 2, 3 -> 3) - 3)
    assert(map == Map(1 -> 1) ++ Map(2 -> 2))
    assert(map == Map(1 -> 1, 2 -> 2, 3 -> 3, 4 -> 4) -- List(3, 4))
    assert(map.keySet == Set(1, 2) && map.values.toSet == Set(1, 2))
    assert(map.empty.isEmpty)
  }

  test("list map") {
    val map = ListMap(3 -> 3, 2 -> 2, 1 -> 1)
    val list = map.keys.toIndexedSeq
    assert(list(0) == 3)
    assert(list(1) == 2)
    assert(list(2) == 1)
  }

  test("sorted map") {
    val map = SortedMap(3 -> 3, 2 -> 2, 1 -> 1)
    val list = map.keys.toIndexedSeq
    assert(list(0) == 1)
    assert(list(1) == 2)
    assert(list(2) == 3)
  }

  test("mutable map") {
    val map = mutable.Map(1 -> 1, 2 -> 2)
    assert((map += 3 -> 3) == Map(1 -> 1, 2 -> 2, 3 -> 3))
    assert((map -= 3) == Map(1 -> 1, 2 -> 2))
    assert((map -= 2) == Map(1 -> 1))
    assert((map -= 1) == Map())
    assert((map ++= List(1 -> 1, 2 -> 2)) == Map(1 -> 1, 2 -> 2))
    assert((map --= List(1, 2)) == Map())
  }
}