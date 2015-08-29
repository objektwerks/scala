package collection

import org.scalatest.FunSuite

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.language.postfixOps

class FunctionalTest extends FunSuite {
  test("seq *") {
    val seq = Seq(1, 2, 3)
    assert(seq.head == 1)
    assert(seq.tail == Seq(2, 3))
    assert(seq.last == 3)
    assert(seq.sum == 6)
    assert(seq.filter(_ > 1) == Seq(2, 3))
    assert(seq.map(_ * 2) == Seq(2, 4, 6))
    assert((seq drop 1) == Seq(2, 3))
    assert(seq.dropWhile(_ < 2) == Seq(2, 3))
    assert(seq.dropRight(1) == Seq(1, 2))
    assert((seq take 2) == Seq(1, 2))
    assert(seq.takeWhile(_ < 3) == Seq(1, 2))
    assert(seq.takeRight(1) == Seq(3))
    assert(seq.slice(0, 2) == Seq(1, 2))
    assert(seq.mkString(", ") == "1, 2, 3")
  }
  
  test("diff") {
    val seq = Seq(1, 2)
    val list = List(2, 3)
    assert((seq diff list) == Seq(1))
    assert((list diff seq) == List(3))
  }

  test("filter") {
    val seq = Seq(1, 2, 3)
    assert(seq.filter(_ > 1) == Seq(2, 3))
    assert(seq.filter(_ > 1).map(_ * 2) == Seq(4, 6))
  }

  test("flatten") {
    val list = List(List(1, 2), List(3, 4))
    assert(list.flatten == List(1, 2, 3, 4))

    val seq = Seq(Some(1), None, Some(3), None)
    assert(seq.flatten == Seq(1, 3))
  }

  test("map") {
    val list = List(1, 2)
    val result = list map (_ * 2)
    assert(result == List(2, 4))
  }

  test("flatmap") {
    val seq = Seq("abc")
    assert(seq.flatMap(_.toUpperCase) == Seq('A', 'B', 'C'))

    val map = Map(1 -> "one", 2 -> "two", 3 -> "three")
    assert((1 to map.size flatMap map.get) == Seq("one", "two", "three"))

    def g(v: Int) = List(v - 1, v, v + 1)
    val list = List(1, 2, 3)
    assert(list.map(i => g(i)) == List(List(0, 1, 2), List(1, 2, 3), List(2, 3, 4)))
    assert(list.flatMap(i => g(i)) == List(0, 1, 2, 1, 2, 3, 2, 3, 4))

    val listOfList: List[List[String]] = List(List("a", "b", "c"))
    val flatMappedListOfList = listOfList flatMap (as => as.map(a => a.toUpperCase))
    assert(listOfList.length == 1)
    assert(flatMappedListOfList.length == 3)
  }

  test("fold") {
    val seq = Seq(1, 2, 3)
    assert(seq.foldLeft(3)(_ + _) == 9)
    assert(seq.foldRight(3)(_ + _) == 9)
  }

  test("groupBy") {
    val seq = Seq(1, 2, 3, 4)
    assert(seq.groupBy(_ % 2 == 0) == Map(false -> Seq(1, 3), true -> Seq(2, 4)))
  }

  test("merge") {
    assert(Seq(1, 2, 3) ++ Seq(4, 5, 6) == Seq(1, 2, 3, 4, 5, 6))
    assert((ArrayBuffer(1, 2, 3) ++= List(4, 5, 6)) == ArrayBuffer(1, 2, 3, 4, 5, 6))
    assert(List(1) ::: List(2) == List(1, 2))
    assert((List(1) union List(2)) == List(1, 2))
    assert((List(1, 2, 3, 4) union List(3, 4, 5, 6) distinct) == List(1, 2, 3, 4, 5, 6))
  }

  test("partition") {
    val tupleOfSeqs: (Seq[Int], Seq[Int]) = Seq(1, 2, 3, 4).partition(_ % 2 == 0)
    val expectedTupleOfSeqs: (Seq[Int], Seq[Int]) = (Seq(2, 4), Seq(1, 3))
    assert(tupleOfSeqs == expectedTupleOfSeqs)
  }

  test("reduce") {
    val seq = Seq(1, 2, 3)
    assert(seq.reduceLeft(_ - _) == -4)
    assert(seq.reduceRight(_ - _) == 2)
  }

  test("scan") {
    val seq = Seq(1, 2)
    assert(seq.scanLeft(2)(_ + _) == Seq(2, 3, 5))
    assert(seq.scanRight(2)(_ + _) == Seq(5, 4, 2))
  }

  test("sort") {
    assert(Seq("c", "b", "a").sorted == Seq("a", "b", "c"))
    assert(Seq(3, 2, 1).sortWith(_ < _) == Seq(1, 2, 3))
    assert(Seq(1, 2, 3).sortWith(_ > _) == Seq(3, 2, 1))
  }

  test("span") {
    val tupleOfSeqs: (Seq[Int], Seq[Int]) = Seq(1, 2, 3, 4).span(_ < 3)
    val expectedTupleOfSeqs: (Seq[Int], Seq[Int]) = (Seq(1, 2), Seq(3, 4))
    assert(tupleOfSeqs == expectedTupleOfSeqs)
  }

  test("splitAt") {
    val tupleOfSeqs: (Seq[Int], Seq[Int]) = Seq(1, 2, 3, 4).splitAt(2)
    val expectedTupleOfSeqs: (Seq[Int], Seq[Int]) = (Seq(1, 2), Seq(3, 4))
    assert(tupleOfSeqs == expectedTupleOfSeqs)
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