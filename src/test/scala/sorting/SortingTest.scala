package sorting

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

case class Worker(name: String, task: String)

object Worker {
  implicit def workerOrdering: Ordering[Worker] = Ordering.by(unapply)
}

class SortingTest extends AnyFunSuite with Matchers {
  test("sorting") {
    val unsorted = List(2, 3, 1)
    val asc = unsorted.sorted
    val desc = List(3, 2, 1)
    asc shouldBe List(1, 2, 3)
    unsorted.sortWith(_ > _) shouldBe desc
    desc.sortWith(_ < _) == asc
  }

  test("ordering > sorting") {
    val unsorted = List(Worker("c", "zspace"), Worker("a", "x"), Worker("b", "y"))
    val sorted = unsorted.sorted
    val sortby = unsorted.sortBy(_.name)
    val asc = unsorted.sortWith(_.name < _.name)
    val desc = unsorted.sortWith(_.name > _.name)
    sorted.head shouldBe Worker("a", "x")
    sortby.head shouldBe Worker("a", "x")
    asc.head shouldBe Worker("a", "x")
    desc.head shouldBe Worker("c", "zspace")
  }
}