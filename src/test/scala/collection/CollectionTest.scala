package collection

import org.scalatest.FunSuite

import scala.collection.parallel.immutable.{ParMap, ParSeq, ParSet, ParRange}
import scala.collection.mutable

class CollectionTest extends FunSuite {
  test("foreach") {
    val list = List(1, 2, 3)
    list.foreach(i => assert(i > 0))
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
    val list = List(1, 2, 3)
    val result = for (e <- list if e > 0) yield e * 2
    assert(result == List(2, 4, 6))
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
    var forList = mutable.ListBuffer[Int]()
    for (x <- xs) {
      forList += (x * 2)
    }
    val mapList = mutable.ListBuffer[Int]()
    xs map (_ * 2) foreach (x => mapList += x)
    assert(forList == mutable.ListBuffer(2, 4))
    assert(mapList == mutable.ListBuffer(2, 4))
  }

  test("for > if guard > filter") {
    val letters = List("A", "B", "C", "D", "F")
    val forLetters: List[Option[String]] = for (l <- letters if l == "A") yield Some(l)
    val filterLetters = letters filter (l => l == "A") map (l => Some(l))
    assert(forLetters.head.getOrElse("Z") == "A")
    assert(filterLetters.head.getOrElse("Z") == "A")
  }

  test("iterator") {
    val result = (1 to 10000000).iterator.map(_ % 10).filter(_ > 5).sum
    assert(result == 30000000)
  }

  test("non view") {
    val result = (1 to 10000000).map(_ % 10).filter(_ > 5).sum
    assert(result == 30000000)
  }

  test("view") {
    val result = (1 to 10000000).view.map(_ % 10).filter(_ > 5).sum
    assert(result == 30000000)
  }

  test("par set") {
    val set = ParSet(1 to 1000000:_*)
    assert(set.sum == 1784293664)
  }

  test("par map") {
    val m = for (i <- 1 to 1000000) yield (i , i)
    val map = ParMap(m:_*)
    assert(map.values.sum == 1784293664)
  }

  test("par seq") {
    val seq = ParSeq(1 to 1000000:_*)
    assert(seq.sum == 1784293664)
  }

  test("par range") {
    val range = ParRange(1, 1000000, 1, inclusive = true)
    assert(range.sum == 1784293664)
  }
}