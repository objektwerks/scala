package sorting

import org.scalatest.{FunSuite, Matchers}

class ParallelSortingTest extends FunSuite with Matchers {
  test("parallel sort") {
    import scala.util.Random.nextInt

    val array = Array.fill(100)(nextInt)
    array.length shouldBe 100
    parallelSort(array) shouldEqual parSort(array)
  }

  def parallelSort[A](seq: Seq[A])(implicit ordering: Ordering[A]): Seq[A] = {
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

  def parSort(array: Array[Int]): Array[Int] = {
    import java.util

    util.Arrays.parallelSort(array)
    array
  }
}