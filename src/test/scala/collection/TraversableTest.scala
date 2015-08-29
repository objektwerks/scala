package collection

import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer

class TraversableTest extends FunSuite {
  test("foreach") {
    val seq = Seq(1, 2, 3)
    seq.foreach(i => assert(i > 0))
    val map = Map("a" -> 1, "b" -> 2, "c" -> 3)
    map.foreach(t => assert(t._1.length == 1 && t._2 > 0))
  }

  test("for") {
    for (i <- 1 to 3) assert(i > 0)
    val map = Map("a" -> 1, "b" -> 2, "c" -> 3)
    for (t <- map) {
      assert(t._1.length == 1 && t._2 > 0)
    }
  }

  test("for > yield") {
    val seq = Seq(1, 2, 3)
    val result = for (e <- seq if e > 0) yield e * 2
    assert(result == Seq(2, 4, 6))
  }

  test("for > flatmap > map") {
    val xs = List(2, 4)
    val ys = List(3, 5)
    val forList = for (x <- xs; y <- ys) yield x * y
    val mapList = xs flatMap { e => ys map { o => e * o } }
    assert(forList == List(2 * 3, 2 * 5, 4 * 3, 4 * 5))
    assert(mapList == List(2 * 3, 2 * 5, 4 * 3, 4 * 5))
  }

  test("for > flatmap > flatmap > map") {
    val xs = List(2, 4)
    val ys = List(3, 5)
    val zs = List(1, 6)
    val forList = for (x <- xs; y <- ys; z <- zs) yield x * y * z
    val mapList = xs flatMap { x => ys flatMap { y => { zs map { z => x * y * z } } } }
    assert(forList == List(6, 36, 10, 60, 12, 72, 20, 120))
    assert(mapList == List(6, 36, 10, 60, 12, 72, 20, 120))
  }

  test("for > foreach > map") {
    val xs = List(1, 2)
    var forList = ListBuffer[Int]()
    for (x <- xs) {
      forList += (x * 2)
    }
    val mapList = ListBuffer[Int]()
    xs map (_ * 2) foreach (x => mapList += x)
    assert(forList == ListBuffer(2, 4))
    assert(mapList == ListBuffer(2, 4))
  }

  test("for > if guard > filter") {
    val letters = List("A", "B", "C", "D", "F")
    val forLetters: List[Option[String]] = for (l <- letters if l == "A") yield Some(l)
    val filterLetters = letters filter (l => l == "A") map (l => Some(l))
    assert(forLetters.head.getOrElse("Z") == "A")
    assert(filterLetters.head.getOrElse("Z") == "A")
  }
}