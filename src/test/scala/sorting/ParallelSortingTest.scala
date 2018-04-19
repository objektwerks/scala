package sorting

import org.scalatest.{FunSuite, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.Random.nextInt

class ParallelSortingTest extends FunSuite with Matchers {
  test("parallel sort") {
    val array = Array.fill(100)(nextInt)
    array.length shouldBe 100
    parallelSort(array) shouldEqual parSort(array)
  }

  def parallelSort(array: Array[Int]): Array[Int] = {
    val (left, right) = array.splitAt(array.length / 2)

    val leftFuture = Future { left.sorted }
    val rightFuture = Future { right.sorted }

    val sortedFuture = for {
      l <- leftFuture
      r <- rightFuture
    } yield (l ++ r).sorted

    val sortedArray = Await.result(sortedFuture, 1 second)
    sortedArray
  }

  def parSort(array: Array[Int]): Array[Int] = {
    val parArray = array.par
    val sortedArray = new Array[Int](parArray.length)
    parArray.copyToArray(sortedArray)
    parallelSort(sortedArray)
  }
}