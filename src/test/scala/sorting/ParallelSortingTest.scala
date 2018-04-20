package sorting

import org.scalatest.{FunSuite, Matchers}

class ParallelSortingTest extends FunSuite with Matchers {
  import scala.util.Random.nextInt

  val array = Array.fill(1000000)(nextInt)
  array.length shouldBe 1000000

  test("scala parallel sort") {
    scalaParallelSort(array)
  }

  test("java parallel sort") {
    javaParallelSort(array)
  }

  test("scala parallel sort equals java parallel sort") {
    scalaParallelSort(array) shouldEqual javaParallelSort(array)
  }

  def scalaParallelSort[A](seq: Seq[A])(implicit ordering: Ordering[A]): Seq[A] = {
    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.duration._
    import scala.concurrent.{Await, Future}

    val (left, right) = seq.splitAt(seq.length / 2)

    val leftFuture = Future { left.sorted }
    val rightFuture = Future { right.sorted }

    val sortedFuture = for {
      l <- leftFuture
      r <- rightFuture
    } yield (l ++ r).sorted

    Await.result(sortedFuture, 1 second)
  }

  def javaParallelSort(array: Array[Int]): Array[Int] = {
    import java.util

    util.Arrays.parallelSort(array)
    array
  }
}