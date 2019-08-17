package sorting

import org.scalatest.{FunSuite, Matchers}

import scala.util.Random.nextInt

class ParallelSortingTest extends FunSuite with Matchers {
  val randomArray = Array.fill(10000)(nextInt)

  test("scala parallel sort") {
    scalaParallelSort(randomArray)
  }

  test("java parallel sort") {
    javaParallelSort(randomArray)
  }

  test("scala parallel sort equals java parallel sort") {
    scalaParallelSort(randomArray) shouldEqual javaParallelSort(randomArray)
  }

  def scalaParallelSort(array: Array[Int])(implicit ordering: Ordering[Int]): Array[Int] = {
    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.duration._
    import scala.concurrent.{Await, Future}

    val (left, right) = array.splitAt(array.length / 2)

    val leftFuture = Future { left.sorted }
    val rightFuture = Future { right.sorted }

    val sortedFuture = for {
      l <- leftFuture
      r <- rightFuture
    } yield (l ++ r).sorted

    Await.result(sortedFuture, 3 seconds)
  }

  def javaParallelSort(array: Array[Int]): Array[Int] = {
    import java.util
    util.Arrays.parallelSort(array)
    array
  }
}