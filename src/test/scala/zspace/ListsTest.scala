package zspace

import org.scalatest.{FunSuite, Matchers}

import scala.annotation.tailrec
import scala.util.Try

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
}