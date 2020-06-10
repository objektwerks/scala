package collection

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.collection.{SortedSet, mutable}

class SetTest extends AnyFunSuite with Matchers {
  test("set") {
    val set = Set(1, 2)
    set shouldEqual Set(1) + 2
    set shouldEqual Set(1, 2, 3) - 3
    set shouldEqual Set(1) ++ Set(2)
    set shouldEqual Set(1, 2, 3, 4) -- List(3, 4)
    set shouldEqual (Set(-1, 0, 1, 2) & Set(1, 2, 3, 4))
    Set(-1, 0) shouldEqual (Set(-1, 0, 1, 2) &~ Set(1, 2, 3, 4))
    Set(3, 4) shouldEqual (Set(1, 2, 3, 4) &~ Set(-1, 0, 1, 2))
    set.size shouldEqual 2
    set.contains(1) shouldBe true
    set.contains(2) shouldBe true
    set.empty.isEmpty shouldBe true
    val a = Set(1, 2, 3,4, 5, 6)
    val b = Set(3, 4, 7, 8, 9, 10)
    a.intersect(b) shouldEqual Set(3, 4)
    a.union(b) shouldEqual Set(5, 10, 1, 6, 9, 2, 7, 3, 8, 4)
    a.diff(b) shouldEqual Set(5, 1, 6, 2)
  }

  test("sorted set") {
    val set = SortedSet(3, 2, 1)
    val list = set.toIndexedSeq
    list(0) shouldEqual 1
    list(1) shouldEqual 2
    list(2) shouldEqual 3
  }

  test("mutable set") {
    val set = mutable.Set(1, 2)
    (set += 3) shouldEqual Set(1, 2, 3)
    (set -= 3) shouldEqual Set(1, 2)
    (set -= 2) shouldEqual Set(1)
    (set -= 1) shouldEqual Set()
    (set ++= List(1, 2)) shouldEqual Set(1, 2)
    (set --= List(1, 2)) shouldEqual Set()
  }
}