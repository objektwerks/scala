package objektwerks.collection

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

class ForTest extends AnyFunSuite with Matchers {
  test("foreach") {
    val map = Map(1 -> 1, 2 -> 2, 3 -> 3)
    map.foreach( keyValue => (keyValue._1 > 0) && (keyValue._2 > 0) shouldBe true )
  }

  test("forall") {
    val list = List(1, 2, 3)
    list.forall( _ > 0 ) shouldBe true
  }

  test("for") {
    for (i <- 1 to 3) i shouldEqual i

    val set = Set(1, 2, 3)
    for (v <- set) v shouldEqual v

    val map = Map(1 -> 1, 2 -> 2, 3 -> 3)
    for ((_, v) <- map) v shouldEqual v
  }

  test("map > foreach") {
    val buffer = mutable.ListBuffer[Int]()
    List(1, 2, 3) map (_ * 2) foreach (x => buffer += x)
    buffer shouldEqual mutable.ListBuffer(2, 4, 6)
  }

  test("for comprehension") {
    val xs = List(1, 2, 3)
    val ys = for {
      x <- xs
    } yield x * 2
    ys shouldEqual xs.map(_ * 2)

    val as = List(List(1), List(2, 3), List(4, 5, 6))
    val bs = for {
      sas <- as
      a <- sas
    } yield a * 2
    bs shouldEqual as.flatMap(_.map( _ * 2 ))
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
    zs shouldEqual List(List(5, 6, 7), List(6, 7, 8), List(7, 8, 9))
    zs.flatten shouldEqual List(5, 6, 7, 6, 7, 8, 7, 8, 9)
    zs.flatten.sum shouldEqual 63
  }

  test("for comprehension > map") {
    val o = Option(3)
    val c = for {
      x <- o map { i => i * i * i }
    } yield x
    c.get shouldEqual 27
  }

  test("for comprehension vs flatmap > map") {
    val xs = List(2, 4)
    val ys = List(3, 5)
    
    val forList = for {
      x <- xs
      y <- ys
    } yield x * y

    val mapList = xs flatMap { x =>
      ys map { y =>
        x * y 
      } 
    }

    forList shouldEqual List(2 * 3, 2 * 5, 4 * 3, 4 * 5)
    mapList shouldEqual List(2 * 3, 2 * 5, 4 * 3, 4 * 5)
  }

  test("for comprehension vs flatmap > flatmap > map") {
    val xs = List(2, 4)
    val ys = List(3, 5)
    val zs = List(1, 6)

    val forList = for {
      x <- xs
      y <- ys
      z <- zs
     } yield x * y * z

    val mapList = xs flatMap { x =>
      ys flatMap { y =>
        { zs map { z =>
          x * y * z } 
        } 
      } 
    }

    forList shouldEqual List(6, 36, 10, 60, 12, 72, 20, 120)
    mapList shouldEqual List(6, 36, 10, 60, 12, 72, 20, 120)
  }

  test("for comprehension > if guard filter") {
    val ls = List("A", "B", "C", "D", "F")
    val filteredLetters = for {
      l <- ls if l == "A"
    } yield l

    val ns = List(-2, -1, 0, 1, 2)
    val filteredNumbers = for {
      n <- ns if n > 0
    } yield n

    filteredLetters.head shouldEqual "A"
    filteredNumbers shouldEqual List(1, 2)
  }

  test("for comprehension > zip") {
    val xs = for {
      (a, b) <- List(1, 2, 3) zip List(4, 5, 6)
    } yield a + b
    xs shouldEqual List(5, 7, 9)
  }

  test("for comprehension with recover") {
    implicit val ec = ExecutionContext.global
    val future = Future(Integer.parseInt("one"))
    val result = ( for {
      i <- future 
    } yield i ).recover { case _: Throwable => -1 }
    result foreach { x => x shouldEqual -1 }
  }
}