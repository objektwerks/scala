package collection

import org.scalatest.FunSuite

import scala.collection.mutable

class ForTest extends FunSuite {
  test("foreach") {
    val map = Map("a" -> 1, "b" -> 2, "c" -> 3)
    map.foreach(t => assert(t._1.length == 1 && t._2 > 0))
  }

  test("forall") {
    val map = Map(1 -> 1, 2 -> 2, 3 -> 3)
    assert(map.forall( _._2 > 0 ))
  }

  test("for") {
    for (i <- 1 to 3) assert(i == i)
    val set = Set(1, 2, 3)
    for (v <- set) assert(v == v)
    val map = Map(1 -> 1, 2 -> 2, 3 -> 3)
    for (k <- map.keys; v <- map.values) assert(k == k && v == v)
    for (e <- map) assert(e._1 > 0 && e._2 > 0)
  }

  test("for > foreach > map") {
    val xs = List(1, 2)
    var forList = mutable.ListBuffer[Int]()
    for (x <- xs) {
      forList += (x * 2)
    }
    val mapList = mutable.ListBuffer[Int]()
    xs map (_ * 2) foreach (x => mapList += x)
    assert(forList == mutable.ListBuffer(2, 4))
    assert(mapList == mutable.ListBuffer(2, 4))
  }

  test("for comprehension") {
    val xs = List(1, 2, 3)
    val ys = for {
      x <- xs
    } yield x * 2
    assert(ys == xs.map(_ * 2))

    val as = List(List(1), List(2, 3), List(4, 5, 6))
    val bs = for {
      sas <- as
      a <- sas
    } yield a * 2
    assert(bs == as.flatMap(_.map( _ * 2)))
  }

  test("nested for comprehensions") {
    val xs = List(1, 2, 3)
    val ys = List(4, 5, 6)
    val zs = for {
      x <- xs
    } yield {
      for {
        y <- ys
      } yield x + y
    }
    assert(zs == List(List(5, 6, 7), List(6, 7, 8), List(7, 8, 9)))
    assert(zs.flatten == List(5, 6, 7, 6, 7, 8, 7, 8, 9))
    assert(zs.flatten.sum == 63)
  }

  test("for comprehension > map") {
    val o = Option(3)
    val c = for {
      x <- o map { i => i * i * i }
    } yield x
    assert(c.get == 27)
  }

  test("for comprehension > flatmap > map") {
    val xs = List(2, 4)
    val ys = List(3, 5)
    val forList = for (x <- xs; y <- ys) yield x * y
    val mapList = xs flatMap { e => ys map { o => e * o } }
    assert(forList == List(2 * 3, 2 * 5, 4 * 3, 4 * 5))
    assert(mapList == List(2 * 3, 2 * 5, 4 * 3, 4 * 5))
  }

  test("for comprehension > flatmap > flatmap > map") {
    val xs = List(2, 4)
    val ys = List(3, 5)
    val zs = List(1, 6)
    val forList = for (x <- xs; y <- ys; z <- zs) yield x * y * z
    val mapList = xs flatMap { x => ys flatMap { y => { zs map { z => x * y * z } } } }
    assert(forList == List(6, 36, 10, 60, 12, 72, 20, 120))
    assert(mapList == List(6, 36, 10, 60, 12, 72, 20, 120))
  }

  test("for comprehension > if guard filter") {
    val filteredLetters = for (l <- List("A", "B", "C", "D", "F") if l == "A") yield l
    val filteredNumbers = for (n <- List(-2, -1, 0, 1, 2) if n > 0) yield n
    assert(filteredLetters.head == "A")
    assert(filteredNumbers == List(1, 2))
  }

  test("for comphrension > zip") {
    val xs = for {
      (a, b) <- List(1, 2, 3) zip List(4, 5, 6)
    } yield a + b
    assert(xs == List(5, 7, 9))
  }
}