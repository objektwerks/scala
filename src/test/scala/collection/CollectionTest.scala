package collection

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.collection.concurrent
import scala.collection.immutable.ListMap
import scala.collection.{SortedMap, SortedSet, mutable}
import scala.jdk.CollectionConverters._

class CollectionTest extends AnyFunSuite with Matchers {
  def toList(i: Int): List[Int] = List(i - 1, i, i + 1)

  test("newBuilder") {
    Array.newBuilder[Int].addOne(1).addOne(2).addOne(3).result() shouldBe Array(1, 2, 3)
    Seq.newBuilder[Int].addOne(1).addOne(2).addOne(3).result() shouldBe Seq(1, 2, 3)
    IndexedSeq.newBuilder[Int].addOne(1).addOne(2).addOne(3).result() shouldBe IndexedSeq(1, 2, 3)
    Set.newBuilder[Int].addOne(1).addOne(2).addOne(3).result() shouldBe Set(1, 2, 3)
  }

  test("list") {
    val list = List(1, 2, 3)

    list shouldBe 1 :: 2 :: 3 :: Nil
    list shouldBe List(1) ::: List(2, 3)
    list shouldBe 1 :: List(2, 3)
    list shouldBe 1 +: List(2, 3)
    list shouldBe List(1, 2) :+ 3
    list shouldBe List(1) ++ List(2, 3)
    list shouldBe List(1) ++: List(2, 3)

    list(2) shouldBe 3 // select by index

    list shouldBe List(1, 1, 2, 2, 3, 3).distinct
    list shouldBe (List(1) concat List(2, 3))
    list shouldBe (List(-2, -1, 0, 1, 2, 3) intersect List(1, 2, 3, 4, 5, 6))

    list.length shouldBe 3
    list.size shouldBe 3
    list.lengthCompare(list.size) shouldBe 0
    list.lengthCompare(list.size - 1) shouldBe 1
    list.nonEmpty shouldBe true
    List().isEmpty shouldBe true

    list.head shouldBe 1
    list.headOption.get shouldBe 1
    list.tail shouldBe List(2, 3)
    list.tails.toList shouldBe List(List(1, 2, 3), List(2, 3), List(3), List())
    list.init shouldBe List(1, 2)
    list.inits.toList shouldBe List(List(1, 2, 3), List(1, 2), List(1), List())
    list.last shouldBe 3
    list.lastOption.get shouldBe 3
    list.lastIndexOf(3) shouldBe 2
    list.lastIndexOfSlice(List(3)) shouldBe 2
    list.lastIndexWhere(_ > 2) shouldBe 2

    list.collect { case i if i % 2 == 0 => i } shouldBe List(2)
    list.collectFirst { case i if i % 2 == 0 => i }.contains(2) shouldBe true
    list.contains(1) shouldBe true
    list.containsSlice(List(2, 3)) shouldBe true
    list.startsWith(List(1, 2)) shouldBe true
    list.endsWith(List(2, 3)) shouldBe true
    list.count(_ > 0) shouldBe 3

    (List(1, 2) diff List(2, 3)) shouldBe List(1)
    (List(2, 3) diff List(1, 2)) shouldBe List(3)

    (list drop 1) shouldBe List(2, 3)
    list.dropWhile(_ < 2) shouldBe List(2, 3)
    list.dropRight(1) shouldBe List(1, 2)

    (list take 2) shouldBe List(1, 2)
    list.takeWhile(_ < 3) shouldBe List(1, 2)
    list.takeRight(1) shouldBe List(3)

    list.min shouldBe 1
    list.minBy(_ * 2) shouldBe 1
    list.max shouldBe 3
    list.maxBy(_ * 2) shouldBe 3

    list.filter(_ > 1) shouldBe List(2, 3)
    list.filter(_ > 1).map(_ * 2) shouldBe List(4, 6)
    list.filterNot(_ > 1) shouldBe List(1)
    list.find(_ > 2).get shouldBe 3

    List(List(1), List(2), List(3)).flatten shouldBe list
    List(Some(1), None, Some(3), None).flatten shouldBe List(1, 3)

    list.map(_ * 2) shouldBe List(2, 4, 6)
    List("abc").map(_.toUpperCase) shouldBe List("ABC")
    list.map(i => toList(i)) shouldBe List(List(0, 1, 2), List(1, 2, 3), List(2, 3, 4))

    list.flatMap(i => List(i * 2)) shouldBe List(2, 4, 6)
    List("abc").flatMap(_.toUpperCase) shouldBe List('A', 'B', 'C')
    list.flatMap(i => toList(i)) shouldBe List(0, 1, 2, 1, 2, 3, 2, 3, 4)

    list.foldLeft(List[Int]())( (tail, head) => head :: tail ) shouldBe List(3, 2, 1)
    val words = List("Hello, ", "world!")
    words.fold("")(_ + _) shouldBe "Hello, world!"
    words.foldLeft("")(_ + _) shouldBe "Hello, world!"
    words.foldRight("")(_ + _) shouldBe "Hello, world!"

    List(2, 4, 6) shouldBe (for (i <- list) yield i * 2)
    List(2, 4, 6) shouldBe (for (i <- list if i > 0) yield i * 2)
    list.forall(_ > 0) shouldBe true
    list foreach { i => i should be > 0 }

    list.groupBy(_ % 2 == 0) shouldBe Map(false -> List(1, 3), true -> List(2))
    list.grouped(1).toList shouldBe List(List(1), List(2), List(3))

    list.indexOf(1) shouldBe 0
    list.indexOfSlice(List(2, 3)) shouldBe 1
    list.indexWhere(_ > 2) shouldBe 2
    list.indices.length shouldBe 3
    for (i <- 0 to 2) list.isDefinedAt(i) shouldBe true

    "123" shouldBe list.mkString

    list.padTo(7, 0) shouldBe List(1, 2, 3, 0, 0, 0, 0)
    list.patch(0, List(4, 5, 6), 3) shouldBe List(4, 5, 6)
    (List[Int](2), List[Int](1, 3)) shouldBe list.partition(_ % 2 == 0)
    list.permutations.toList shouldBe List(List(1, 2, 3), List(1, 3, 2), List(2, 1, 3), List(2, 3, 1), List(3, 1, 2), List(3, 2, 1))
    list.segmentLength(_ > 0) shouldBe 3
    list.product shouldBe 6

    list shouldBe List.range(1, 4)
    list.reduceLeftOption(_ + _).get shouldBe 6
    list.reduceRightOption(_ + _).get shouldBe 6
    list shouldBe List(3, 2, 1).reverse

    list.segmentLength(_ > 0, 0) shouldBe 3

    list shouldBe List(3, 2, 1).sortBy(i => i)
    list shouldBe List(3, 2, 1).sorted
    List(1, 2, 3).sortWith(_ > _) shouldBe List(3, 2, 1)
    List(3, 2, 1).sortWith(_ < _) shouldBe List(1, 2, 3)

    list.scan(0)(_ + _) shouldBe List(0, 1, 3, 6)
    list.scanLeft(0)(_ + _) shouldBe List(0, 1, 3, 6)
    list.scanRight(0)(_ + _) shouldBe List(6, 5, 3, 0)

    list.slice(0, 2) shouldBe List(1, 2)
    List(List(1), List(2), List(3)) shouldBe list.sliding(1).toList
    (List[Int](1), List[Int](2, 3)) shouldBe list.span(_ < 2)
    (List[Int](1, 2), List[Int](3)) shouldBe list.splitAt(2)
    list.sum shouldBe 6

    List(Set(1, 2), Set(3, 4), Set(5, 6)).transpose shouldBe List(List(1, 3, 5), List(2, 4, 6))
    List(1, 2, 1) shouldBe list.updated(index = 2, elem = 1)
    List(2, 4, 6) shouldBe list.withFilter(_ > 0).map(_ * 2)

    (1 to 100).map(_ % 10).filter(_ > 5).sum shouldBe 300 // strict, slowest
    (1 to 100).view.map(_ % 10).filter(_ > 5).sum shouldBe 300  // non-strict, fast
    (1 to 100).iterator.map(_ % 10).filter(_ > 5).sum shouldBe 300  // non-strict, fastest
    (1 to 100).to(LazyList).map(_ % 10).filter(_ > 5).sum shouldBe 300  // non-strict, fastest

    (List[Int](1, 3),List[Int](2, 4)) shouldBe List((1, 2), (3, 4)).unzip
    List((1,3), (2,4)) shouldBe (List(1, 2) zip List(3, 4))
    List((1,3), (2,4), (3,5)) shouldBe List(1, 2, 3).zipAll(List(3, 4, 5), 0, 1)
    List((1,0), (2,1), (3,2)) shouldBe list.zipWithIndex
  }

  test("lazy list") {
    val numberOfEvens = (1 to 100).to(LazyList).count(_ % 2 == 0)
    numberOfEvens shouldBe 50
  }

  test("list buffer") {
    val buffer = mutable.ListBuffer(1, 2)
    (buffer += 3) shouldBe mutable.ListBuffer(1, 2, 3)
    (buffer -= 3) shouldBe mutable.ListBuffer(1, 2)
    (buffer -= 2) shouldBe mutable.ListBuffer(1)
    (buffer -= 1) shouldBe mutable.ListBuffer()
  }

  test("view") {
    val vector = Vector(1, 2, 3)
    val view = vector.view
    view.map(_ + 1).map(_ * 2).to(Vector) shouldBe Vector(4, 6, 8)
  }

  test("vector") {
    val vector = Vector(1, 2)
    vector.length shouldBe 2
    vector(0) shouldBe 1
    vector(1) shouldBe 2
    vector.reverse shouldBe Vector(2, 1)
    vector shouldBe 1 +: Vector(2)
    vector shouldBe Vector(1) :+ 2
    vector shouldBe Vector(1) ++ Vector(2)
    vector shouldBe Vector(1) ++: Vector(2)
    3 shouldBe (vector foldRight 0)(_ + _)
  }

  test("array") {
    val array = Array(1, 2)
    1 +: Array(2) shouldBe array
    Array(1) :+ 2 shouldBe array
    Array(1) ++ Array(2) shouldBe array
    Array(1) ++: Array(2) shouldBe array
  }

  test("array buffer") {
    val buffer = mutable.ArrayBuffer(1, 2)
    (buffer += 3) shouldBe mutable.ArrayBuffer(1, 2, 3)
    (buffer -= 3) shouldBe mutable.ArrayBuffer(1, 2)
  }

  test("array deque") {
    val deque = mutable.ArrayDeque(1, 2)
    (deque += 3) shouldBe mutable.ArrayDeque(1, 2, 3)
    (deque -= 3) shouldBe mutable.ArrayDeque(1, 2)
  }

  test("queue") {
    val queue = mutable.Queue(1, 2)
    queue enqueue 3
    3 shouldBe queue.last
    queue.dequeue() shouldBe 1
    queue.dequeue() shouldBe 2
    queue.dequeue() shouldBe 3
    queue.isEmpty shouldBe true
  }

  test("stack") {
    val stack = mutable.Stack(2, 1)
    stack push 3
    3 shouldBe stack.pop()
    2 shouldBe stack.pop()
    1 shouldBe stack.pop()
    stack.isEmpty shouldBe true
  }

  test("string builder") {
    val builder = new StringBuilder
    builder.append("a")
    builder.append("b")
    builder.append("c")
    builder.toString shouldBe "abc"
    builder.result() shouldBe "abc"
    builder.reverse.result() shouldBe "cba"
  }

  test("range") {
    (1 until 10) shouldBe Range(1, 10, 1)
    (10 until 1 by -1) shouldBe Range(10, 1, -1)
    (1 to 10) shouldBe Range.inclusive(1, 10, 1)
  }

  test("map") {
    val map = Map(1 -> 1, 2 -> 2)
    map(1) shouldBe 1
    map(2) shouldBe 2
    map.getOrElse(3, -1) shouldBe -1
    map.contains(1) shouldBe true
    map shouldBe Map(1 -> 1) + (2 -> 2)
    map shouldBe Map(1 -> 1, 2 -> 2, 3 -> 3) - 3
    map shouldBe Map(1 -> 1) ++ Map(2 -> 2)
    map shouldBe Map(1 -> 1, 2 -> 2, 3 -> 3, 4 -> 4) -- List(3, 4)
    map.keySet shouldBe Set(1, 2)
    map.values.toSet shouldBe Set(1, 2)
    map.empty.isEmpty shouldBe true
  }

  test("list map") {
    val map = ListMap(3 -> 3, 2 -> 2, 1 -> 1)
    val list = map.keys.toIndexedSeq
    list(0) shouldBe 3
    list(1) shouldBe 2
    list(2) shouldBe 1
  }

  test("sorted map") {
    val map = SortedMap(3 -> 3, 2 -> 2, 1 -> 1)
    val list = map.keys.toIndexedSeq
    list(0) shouldBe 1
    list(1) shouldBe 2
    list(2) shouldBe 3
  }

  test("mutable map") {
    val map = mutable.Map(1 -> 1, 2 -> 2)
    (map += 3 -> 3) shouldBe Map(1 -> 1, 2 -> 2, 3 -> 3)
    (map -= 3) shouldBe Map(1 -> 1, 2 -> 2)
    (map -= 2) shouldBe Map(1 -> 1)
    (map -= 1) shouldBe Map()
    (map ++= List(1 -> 1, 2 -> 2)) shouldBe Map(1 -> 1, 2 -> 2)
    (map --= List(1, 2)) shouldBe Map()
  }

  test("trie map") {
    val map = concurrent.TrieMap(1 -> 1, 2 -> 2)
    map.getOrElseUpdate(3, 3) shouldBe 3
    map.remove(3) shouldBe Some(3)
    map.addOne(3 -> 3) shouldBe concurrent.TrieMap(1 -> 1, 2 -> 2, 3 -> 3)
  }

  test("set") {
    val set = Set(1, 2)
    set shouldBe Set(1) + 2
    set shouldBe Set(1, 2, 3) - 3
    set shouldBe Set(1) ++ Set(2)
    set shouldBe Set(1, 2, 3, 4) -- List(3, 4)
    set shouldBe (Set(-1, 0, 1, 2) & Set(1, 2, 3, 4))
    Set(-1, 0) shouldBe (Set(-1, 0, 1, 2) &~ Set(1, 2, 3, 4))
    Set(3, 4) shouldBe (Set(1, 2, 3, 4) &~ Set(-1, 0, 1, 2))
    set.size shouldBe 2
    set.contains(1) shouldBe true
    set.contains(2) shouldBe true
    set.empty.isEmpty shouldBe true
    val a = Set(1, 2, 3,4, 5, 6)
    val b = Set(3, 4, 7, 8, 9, 10)
    a.intersect(b) shouldBe Set(3, 4)
    a.union(b) shouldBe Set(5, 10, 1, 6, 9, 2, 7, 3, 8, 4)
    a.diff(b) shouldBe Set(5, 1, 6, 2)
  }

  test("sorted set") {
    val set = SortedSet(3, 2, 1)
    val list = set.toIndexedSeq
    list(0) shouldBe 1
    list(1) shouldBe 2
    list(2) shouldBe 3
  }

  test("mutable set") {
    val set = mutable.Set(1, 2)
    (set += 3) shouldBe Set(1, 2, 3)
    (set -= 3) shouldBe Set(1, 2)
    (set -= 2) shouldBe Set(1)
    (set -= 1) shouldBe Set()
    (set ++= List(1, 2)) shouldBe Set(1, 2)
    (set --= List(1, 2)) shouldBe Set()
  }

  test("tupled") {
    case class CityStateZip(city: String, state: String, zip: Int) {
      def tupled: (String, String, Int) = (city, state, zip)
    }

    val (city, state, zip) = CityStateZip("placida", "florida", 33946).tupled
    city shouldBe "placida"
    state shouldBe "florida"
    zip shouldBe 33946
  }

  test("tuple") {
    val (first, last, age) = ("fred", "flintstone", 99)
    first shouldBe "fred"
    last shouldBe "flintstone"
    age shouldBe 99
  }

  test("copy") {
    case class KeyValue(key: Int, value: Int) {
      def tupled: (Int, Int) = (key, value)
    }
    
    (2, 2) shouldBe KeyValue(1, 1).tupled.copy(2, 2)
  }

  test("asJava") {
    val asJavaList = List(1, 2, 3).asJava
    asJavaList.size shouldBe 3
    asJavaList.stream.count shouldBe 3
    asJavaList.stream.reduce((t: Int, u: Int) => t + u).get shouldBe 6
  }

  test("asScala") {
    val arrayList = new java.util.ArrayList[Int]()
    arrayList.add(1)
    arrayList.add(2)
    arrayList.add(3)
    val asScalaBuffer = arrayList.asScala
    asScalaBuffer.size shouldBe 3
    asScalaBuffer.sum shouldBe 6
  }  
}