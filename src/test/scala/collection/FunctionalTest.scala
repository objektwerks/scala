package collection

import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps

class FunctionalTest extends FunSuite {
  test("list *") {
    val list = List(1, 2, 3)
    assert(list.head == 1)
    assert(list.tail == List(2, 3))
    assert(list.last == 3)
    assert(list.sum == 6)
    assert(list.filter(_ > 1) == List(2, 3))
    assert(list.map(_ * 2) == List(2, 4, 6))
    assert((list drop 1) == List(2, 3))
    assert(list.dropWhile(_ < 2) == List(2, 3))
    assert(list.dropRight(1) == List(1, 2))
    assert((list take 2) == List(1, 2))
    assert(list.takeWhile(_ < 3) == List(1, 2))
    assert(list.takeRight(1) == List(3))
    assert(list.slice(0, 2) == List(1, 2))
    assert(list.mkString(", ") == "1, 2, 3")
  }
  
  test("diff") {
    val xs = List(1, 2)
    val ys = List(2, 3)
    assert((xs diff ys) == List(1))
    assert((ys diff xs) == List(3))
  }

  test("filter") {
    val list = List(1, 2, 3)
    assert(list.filter(_ > 1) == List(2, 3))
    assert(list.filter(_ > 1).map(_ * 2) == List(4, 6))
  }

  test("flatten") {
    val xs = List(List(1, 2), List(3, 4))
    assert(xs.flatten == List(1, 2, 3, 4))

    val ys = List(Some(1), None, Some(3), None)
    assert(ys.flatten == List(1, 3))
  }

  test("map") {
    val list = List(1, 2)
    val result = list map (_ * 2)
    assert(result == List(2, 4))
  }

  test("flatmap") {
    val xs = List("abc")
    assert(xs.flatMap(_.toUpperCase) == List('A', 'B', 'C'))

    val map = Map(1 -> "one", 2 -> "two", 3 -> "three")
    assert((1 to map.size flatMap map.get) == List("one", "two", "three"))

    def g(v: Int) = List(v - 1, v, v + 1)
    val ys = List(1, 2, 3)
    assert(ys.map(i => g(i)) == List(List(0, 1, 2), List(1, 2, 3), List(2, 3, 4)))
    assert(ys.flatMap(i => g(i)) == List(0, 1, 2, 1, 2, 3, 2, 3, 4))

    val listOfList: List[List[String]] = List(List("a", "b", "c"))
    val flatMappedListOfList = listOfList flatMap (as => as.map(a => a.toUpperCase))
    assert(listOfList.length == 1)
    assert(flatMappedListOfList.length == 3)
  }

  test("fold") {
    val list = List(1, 2, 3)
    assert(list.foldLeft(3)(_ + _) == 9)
    assert(list.foldRight(3)(_ + _) == 9)
  }

  test("groupBy") {
    val list = List(1, 2, 3, 4)
    assert(list.groupBy(_ % 2 == 0) == Map(false -> List(1, 3), true -> List(2, 4)))
  }

  test("merge") {
    assert(List(1, 2, 3) ++ List(4, 5, 6) == List(1, 2, 3, 4, 5, 6))
    assert((ArrayBuffer(1, 2, 3) ++= List(4, 5, 6)) == ArrayBuffer(1, 2, 3, 4, 5, 6))
    assert(List(1) ::: List(2) == List(1, 2))
    assert((List(1) union List(2)) == List(1, 2))
    assert((List(1, 2, 3, 4) union List(3, 4, 5, 6) distinct) == List(1, 2, 3, 4, 5, 6))
  }

  test("partition") {
    val tupleOfLists: (List[Int], List[Int]) = List(1, 2, 3, 4).partition(_ % 2 == 0)
    val expectedTupleOfLists: (List[Int], List[Int]) = (List(2, 4), List(1, 3))
    assert(tupleOfLists == expectedTupleOfLists)
  }

  test("reduce") {
    val list = List(1, 2, 3)
    assert(list.reduceLeft(_ - _) == -4)
    assert(list.reduceRight(_ - _) == 2)
  }

  test("scan") {
    val list = List(1, 2)
    assert(list.scanLeft(2)(_ + _) == List(2, 3, 5))
    assert(list.scanRight(2)(_ + _) == List(5, 4, 2))
  }

  test("sort") {
    assert(List("c", "b", "a").sorted == List("a", "b", "c"))
    assert(List(3, 2, 1).sortWith(_ < _) == List(1, 2, 3))
    assert(List(1, 2, 3).sortWith(_ > _) == List(3, 2, 1))
  }

  test("span") {
    val tupleOfLists: (List[Int], List[Int]) = List(1, 2, 3, 4).span(_ < 3)
    val expectedTupleOfLists: (List[Int], List[Int]) = (List(1, 2), List(3, 4))
    assert(tupleOfLists == expectedTupleOfLists)
  }

  test("splitAt") {
    val tupleOfLists: (List[Int], List[Int]) = List(1, 2, 3, 4).splitAt(2)
    val expectedTupleOfLists: (List[Int], List[Int]) = (List(1, 2), List(3, 4))
    assert(tupleOfLists == expectedTupleOfLists)
  }

  test("unzip") {
    val tupleOfLists: (List[AnyVal], List[AnyVal]) = List((1, 2), ('a', 'b')).unzip
    val expectedTupleOfLists: (List[AnyVal], List[AnyVal]) = (List(1, 'a'), List(2, 'b'))
    assert(tupleOfLists == expectedTupleOfLists)
  }

  test("zip") {
    val wives = List("wilma", "betty")
    val husbands = List("fred", "barney")
    assert((wives zip husbands) == List(("wilma", "fred"), ("betty", "barney")))
  }
}