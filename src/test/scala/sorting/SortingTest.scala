package sorting

import org.scalatest.{FunSuite, Matchers}

case class Worker(name: String, task: String)
object Worker {
  implicit def defaultOrdering: Ordering[Worker] = Ordering.by(_.name)
}

class SortingTest extends FunSuite with Matchers {
  test("sorting") {
    val unsorted = List(2, 3, 1)
    val asc = unsorted.sorted
    val desc = List(3, 2, 1)
    asc shouldBe asc
    unsorted.sortBy(i => i) shouldBe asc
    unsorted.sortWith(_ > _) shouldBe desc
    desc.sortWith(_ < _) == asc
  }

  test("ordering") {
    val unsorted = List(Worker("c", "z"), Worker("a", "x"), Worker("b", "y"))
    val sorted = unsorted.sorted
    val sortby = unsorted.sortBy(Worker.unapply)
    val asc = unsorted.sortWith(_.name < _.name)
    val desc = unsorted.sortWith(_.name > _.name)
    sorted.head shouldBe Worker("a", "x")
    sortby.head shouldBe Worker("a", "x")
    asc.head shouldBe Worker("a", "x")
    desc.head shouldBe Worker("c", "z")
  }
}