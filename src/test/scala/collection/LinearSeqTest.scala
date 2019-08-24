package collection

import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable

class LinearSeqTest extends FunSuite with Matchers {
  def toList(i: Int): List[Int] = List(i - 1, i, i + 1)

  test("list") {
    val list = List(1, 2, 3)

    list shouldEqual 1 :: 2 :: 3 :: Nil
    list shouldEqual List(1) ::: List(2, 3)
    list shouldEqual 1 :: List(2, 3)
    list shouldEqual 1 +: List(2, 3)
    list shouldEqual List(1, 2) :+ 3
    list shouldEqual List(1) ++ List(2, 3)
    list shouldEqual List(1) ++: List(2, 3)

    list(2) shouldEqual 3 // select by index

    list shouldEqual List(1, 1, 2, 2, 3, 3).distinct
    list shouldEqual (List(1) concat List(2, 3))
    list shouldEqual (List(-2, -1, 0, 1, 2, 3) intersect List(1, 2, 3, 4, 5, 6))

    list.length shouldEqual 3
    list.size shouldEqual 3
    list.lengthCompare(list.size) shouldEqual 0
    list.lengthCompare(list.size - 1) shouldEqual 1
    list.nonEmpty shouldBe true
    List().isEmpty shouldBe true

    list.head shouldEqual 1
    list.headOption.get shouldEqual 1
    list.tail shouldEqual List(2, 3)
    list.tails.toList shouldEqual List(List(1, 2, 3), List(2, 3), List(3), List())
    list.init shouldEqual List(1, 2)
    list.inits.toList shouldEqual List(List(1, 2, 3), List(1, 2), List(1), List())
    list.last shouldEqual 3
    list.lastOption.get shouldEqual 3
    list.lastIndexOf(3) shouldEqual 2
    list.lastIndexOfSlice(List(3)) shouldEqual 2
    list.lastIndexWhere(_ > 2) shouldEqual 2

    list.collect { case i if i % 2 == 0 => i } shouldEqual List(2)
    list.collectFirst { case i if i % 2 == 0 => i }.contains(2) shouldBe true
    list.contains(1) shouldBe true
    list.containsSlice(List(2, 3)) shouldBe true
    list.startsWith(List(1, 2)) shouldBe true
    list.endsWith(List(2, 3)) shouldBe true
    list.count(_ > 0) shouldEqual 3

    (List(1, 2) diff List(2, 3)) shouldEqual List(1)
    (List(2, 3) diff List(1, 2)) shouldEqual List(3)

    (list drop 1) shouldEqual List(2, 3)
    list.dropWhile(_ < 2) shouldEqual List(2, 3)
    list.dropRight(1) shouldEqual List(1, 2)

    (list take 2) shouldEqual List(1, 2)
    list.takeWhile(_ < 3) shouldEqual List(1, 2)
    list.takeRight(1) shouldEqual List(3)

    list.min shouldEqual 1
    list.minBy(_ * 2) shouldEqual 1
    list.max shouldEqual 3
    list.maxBy(_ * 2) shouldEqual 3

    list.filter(_ > 1) shouldEqual List(2, 3)
    list.filter(_ > 1).map(_ * 2) shouldEqual List(4, 6)
    list.filterNot(_ > 1) shouldEqual List(1)
    list.find(_ > 2).get shouldEqual 3

    List(List(1), List(2), List(3)).flatten shouldEqual list
    List(Some(1), None, Some(3), None).flatten shouldEqual List(1, 3)

    list.map(_ * 2) shouldEqual List(2, 4, 6)
    List("abc").map(_.toUpperCase) shouldEqual List("ABC")
    list.map(i => toList(i)) shouldEqual List(List(0, 1, 2), List(1, 2, 3), List(2, 3, 4))

    list.flatMap(i => List(i * 2)) shouldEqual List(2, 4, 6)
    List("abc").flatMap(_.toUpperCase) shouldEqual List('A', 'B', 'C')
    list.flatMap(i => toList(i)) shouldEqual List(0, 1, 2, 1, 2, 3, 2, 3, 4)

    list.foldLeft(List[Int]())( (tail, head) => head :: tail ) shouldEqual List(3, 2, 1)
    val words = List("Hello, ", "world!")
    words.fold("")(_ + _) shouldEqual "Hello, world!"
    words.foldLeft("")(_ + _) shouldEqual "Hello, world!"
    words.foldRight("")(_ + _) shouldEqual "Hello, world!"

    List(2, 4, 6) shouldEqual (for (i <- list) yield i * 2)
    List(2, 4, 6) shouldEqual (for (i <- list if i > 0) yield i * 2)
    list.forall(_ > 0) shouldBe true
    list foreach { i => i should be > 0 }

    list.groupBy(_ % 2 == 0) shouldEqual Map(false -> List(1, 3), true -> List(2))
    list.grouped(1).toList shouldEqual List(List(1), List(2), List(3))

    list.indexOf(1) shouldEqual 0
    list.indexOfSlice(List(2, 3)) shouldEqual 1
    list.indexWhere(_ > 2) shouldEqual 2
    list.indices.length shouldEqual 3
    for (i <- 0 to 2) list.isDefinedAt(i) shouldBe true

    "123" shouldEqual list.mkString

    list.padTo(7, 0) shouldEqual List(1, 2, 3, 0, 0, 0, 0)
    list.patch(0, List(4, 5, 6), 3) shouldEqual List(4, 5, 6)
    (List[Int](2), List[Int](1, 3)) shouldEqual list.partition(_ % 2 == 0)
    list.permutations.toList shouldEqual List(List(1, 2, 3), List(1, 3, 2), List(2, 1, 3), List(2, 3, 1), List(3, 1, 2), List(3, 2, 1))
    list.segmentLength(_ > 0) shouldEqual 3
    list.product shouldEqual 6

    list shouldEqual List.range(1, 4)
    list.reduceLeftOption(_ + _).get shouldEqual 6
    list.reduceRightOption(_ + _).get shouldEqual 6
    list shouldEqual List(3, 2, 1).reverse

    list.segmentLength(_ > 0, 0) shouldEqual 3

    list shouldEqual List(3, 2, 1).sortBy(i => i)
    list shouldEqual List(3, 2, 1).sorted
    List(1, 2, 3).sortWith(_ > _) shouldEqual List(3, 2, 1)
    List(3, 2, 1).sortWith(_ < _) shouldEqual List(1, 2, 3)

    list.scan(0)(_ + _) shouldEqual List(0, 1, 3, 6)
    list.scanLeft(0)(_ + _) shouldEqual List(0, 1, 3, 6)
    list.scanRight(0)(_ + _) shouldEqual List(6, 5, 3, 0)

    list.slice(0, 2) shouldEqual List(1, 2)
    List(List(1), List(2), List(3)) shouldEqual list.sliding(1).toList
    (List[Int](1), List[Int](2, 3)) shouldEqual list.span(_ < 2)
    (List[Int](1, 2), List[Int](3)) shouldEqual list.splitAt(2)
    list.sum shouldEqual 6

    List(Set(1, 2), Set(3, 4), Set(5, 6)).transpose shouldEqual List(List(1, 3, 5), List(2, 4, 6))
    List(1, 2, 1) shouldEqual list.updated(index = 2, elem = 1)
    List(2, 4, 6) shouldEqual list.withFilter(_ > 0).map(_ * 2)

    (1 to 100).map(_ % 10).filter(_ > 5).sum shouldEqual 300 // strict, slowest
    (1 to 100).view.map(_ % 10).filter(_ > 5).sum shouldEqual 300  // non-strict, fast
    (1 to 100).iterator.map(_ % 10).filter(_ > 5).sum shouldEqual 300  // non-strict, fastest
    (1 to 100).to(LazyList).map(_ % 10).filter(_ > 5).sum shouldEqual 300  // non-strict, fastest

    (List[Int](1, 3),List[Int](2, 4)) shouldEqual List((1, 2), (3, 4)).unzip
    List((1,3), (2,4)) shouldEqual (List(1, 2) zip List(3, 4))
    List((1,3), (2,4), (3,5)) shouldEqual List(1, 2, 3).zipAll(List(3, 4, 5), 0, 1)
    List((1,0), (2,1), (3,2)) shouldEqual list.zipWithIndex
  }

  test("lazy list") {
    val numberOfEvens = (1 to 100).to(LazyList).count(_ % 2 == 0)
    numberOfEvens shouldEqual 50
  }

  test("list buffer") {
    val buffer = mutable.ListBuffer(1, 2)
    (buffer += 3) shouldEqual mutable.ListBuffer(1, 2, 3)
    (buffer -= 3) shouldEqual mutable.ListBuffer(1, 2)
    (buffer -= 2) shouldEqual mutable.ListBuffer(1)
    (buffer -= 1) shouldEqual mutable.ListBuffer()
  }
}