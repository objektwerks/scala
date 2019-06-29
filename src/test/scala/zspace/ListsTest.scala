package zspace

import org.scalatest.{FunSuite, Matchers}

import scala.annotation.tailrec
import scala.util.Try

/**
  * 1. For a single-linked (forward only) list write a function that returns 5th element from the end of the list. The
  * list can only be walked once (reverse, length, or size of this list cannot be used).
  * 2. Given two lists, write a function that answers if all elements of one list are in the other.
  * 3. Write a function that intersects 2 lists.
  */
object Lists {
  def findElementInList[A](list: List[A], element: Int): Option[A] = {
    @tailrec
    def reverse(list: List[A], acc: List[A] = List.empty[A]): List[A] = list match {
      case Nil => acc
      case head :: tail => reverse(tail, head :: acc)
    }
    Try { reverse(list)(element - 1) }.toOption
  }

  def isListAInListB[A](listA: List[A], listB: List[A]): Boolean = listA.count(a => listB.contains(a)) == listA.length

  @tailrec
  def intersectLists[A](listA: List[A],
                        listB: List[A],
                        acc: List[A] = List.empty[A]): List[A] =
    listA match {
      case Nil => acc
      case head :: tail =>
        if (listB.contains(head)) {
          intersectLists(tail, listB, acc :+ head)
        } else {
          intersectLists(tail, listB, acc)
        }
    }
}

class ListsTest extends FunSuite with Matchers {
  import Lists._

  test("find nth element from end of list") {
    findElementInList( (1 to 10).toList, 5 ) shouldBe Some(6)
    findElementInList( (1 to 10).toList, 13 ) shouldBe None
  }

  test("is listA in listB") {
    isListAInListB((5 to 15).toList, (1 to 20).toList) shouldBe true
    isListAInListB((10 to 30).toList, (15 to 50).toList) shouldBe false
  }

  test("intersect lists") {
    val listA = (1 to 10).toList
    val listB = (5 to 15).toList
    intersectLists(listA, listB) shouldEqual listA.intersect(listB)
  }
}