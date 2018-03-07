package sorting

import org.scalatest.FunSuite

import scala.util.Random

object ParallelSelectionSort {
  def sort(input: Array[Int]): Array[Int] = input
}

class ParallelSelectionSortTest extends FunSuite {
  def alreadySorted = (1 to 1000).toArray
  def reversed = alreadySorted.reverse
  def shuffled = Random.shuffle(alreadySorted.toSeq).toArray
  def emptyArray = Array.empty[Int]
  def singleElement = Array(1)

  test("sort") {
    import ParallelSelectionSort._

    assert(sort(alreadySorted).sameElements(alreadySorted), "Failed sorting a pre-sorted array")
    assert(sort(reversed).sameElements(alreadySorted), "Failed sorting a reversed array")
    assert(sort(shuffled).sameElements(alreadySorted), "Failed sorting a shuffled array")
    assert(sort(emptyArray).sameElements(emptyArray), "Failed sorting an empty array")
    assert(sort(singleElement).sameElements(singleElement), "Failed sorting an array of size 1")
    println("Tests passed!")
  }
}