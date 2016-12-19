package sorting

import org.scalatest.{FunSuite, Matchers}

case class Worker(name: String, task: String)
object Worker {
  implicit def defaultOrdering: Ordering[Worker] = Ordering.by(unapply)
}

class SortingTest extends FunSuite with Matchers {
  test("sorted") {
    val list = List(3, 2, 1)
    list.sorted shouldBe List(1, 2, 3)
  }

  test("implicit sorting") {
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