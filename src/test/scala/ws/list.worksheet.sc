import scala.annotation.tailrec
import scala.util.Try

List() == Nil

val xs = 2 :: Nil
val ys = 1 +: xs
val zs = ys :+ 3
xs ++ ys ++ zs

zs.filter(_ % 2 == 0)
zs.map(_ * 2)
zs.fold(0)(_ + _)
List.empty[Int].fold(0)(_ + _)
zs.reduce(_ + _) // List[Int] can't be empty!
zs.scan(0)(_ + _)
zs.sum

def toInt(s: String): Option[Int] = Try(s.toInt).toOption
val ss = List("1", "2", "3", "four")
ss.map(toInt)
ss.flatMap(toInt)
ss.flatMap(toInt).sum

def sumNonTailRec(xs: List[Int]): Int = xs match {
  case Nil => 0
  case head :: tail => head + sumNonTailRec(tail)
}
sumNonTailRec(zs)
zs.sum

def sumTailRec(xs: List[Int]): Int = {
  @tailrec
  def sum(ys: List[Int], acc: Int = 0): Int = ys match {
    case Nil => acc
    case head :: tail => sum(tail, acc + head)
  }
  sum(xs)
}
sumTailRec(zs)
zs.sum

@tailrec
final def sum(xs: List[Int], acc: Int = 0): Int = xs match {
  case Nil => acc
  case head :: tail => sum(tail, acc + head)
}
sum(zs)
zs.sum

@tailrec
final def product(xs: List[Int], acc: Int = 1): Int = xs match {
  case Nil => acc
  case head :: tail => product(tail, acc * head)
}
product(zs)
zs.product

@tailrec
final def reverse[A](list: List[A], acc: List[A] = List.empty[A]): List[A] = list match {
  case Nil => acc
  case head :: tail => reverse(tail, head :: acc)
}
reverse( (1 to 10).toList )

def findElementReverseRight[A](list: List[A], element: Int): Option[A] = {
  @tailrec
  def reverse(list: List[A], acc: List[A] = List.empty[A]): List[A] = list match {
    case Nil => acc
    case head :: tail => reverse(tail, head :: acc)
  }
  Try { reverse(list)(element - 1) }.toOption
}
val findElements = (1 to 10).toList
findElementReverseRight(findElements, 4)
findElementReverseRight(findElements, 15)

def isListAinListB[A](listA: List[A], listB: List[A]): Boolean = {
  listA.count(a => listB.contains(a)) == listA.length
}
isListAinListB((5 to 15).toList, (1 to 20).toList)
isListAinListB((10 to 30).toList, (15 to 50).toList)

@tailrec
final def intersectLists[A](listA: List[A],
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

val listA = (1 to 10).toList
val listB = (5 to 15).toList
val intersectListsResult = intersectLists(listA, listB)
val intersectSdkResult = listA intersect listB
val intersectListsAndSdkAssert = intersectListsResult == intersectSdkResult