package collection

import org.scalatest.{FunSuite, Matchers}

import scala.collection.{SortedSet, mutable}

class SetTest extends FunSuite with Matchers {
  test("set") {
    val set = Set(1, 2)
    assert(set == Set(1) + 2)
    assert(set == Set(1, 2, 3) - 3)
    assert(set == Set(1) ++ Set(2))
    assert(set == Set(1, 2, 3, 4) -- List(3, 4))
    assert(set == (Set(-1, 0, 1, 2) & Set(1, 2, 3, 4)))
    assert(Set(-1, 0) == (Set(-1, 0, 1, 2) &~ Set(1, 2, 3, 4)))
    assert(Set(3, 4) == (Set(1, 2, 3, 4) &~ Set(-1, 0, 1, 2)))
    assert(set.size == 2 && set.contains(1) && set.contains(2))
    assert(set.empty.isEmpty)
    val a = Set(1, 2, 3,4, 5, 6)
    val b = Set(3, 4, 7, 8, 9, 10)
    assert(a.intersect(b) == Set(3, 4))
    assert(a.union(b) == Set(5, 10, 1, 6, 9, 2, 7, 3, 8, 4))
    assert(a.diff(b) == Set(5, 1, 6, 2))
  }

  test("sorted set") {
    val set = SortedSet(3, 2, 1)
    val list = set.toIndexedSeq
    assert(list(0) == 1)
    assert(list(1) == 2)
    assert(list(2) == 3)
  }

  test("mutable set") {
    val set = mutable.Set(1, 2)
    assert((set += 3) == Set(1, 2, 3))
    assert((set -= 3) == Set(1, 2))
    assert((set -= 2) == Set(1))
    assert((set -= 1) == Set())
    assert((set ++= List(1, 2)) == Set(1, 2))
    assert((set --= List(1, 2)) == Set())
  }
}