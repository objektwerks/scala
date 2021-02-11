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

def toInt(s: String): Option[Int] = s.toIntOption
val ss = List("1", "2", "3", "four")
ss.map(toInt)
ss.map(toInt).collect { case Some(i) => i }
ss.map(toInt).flatten
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
product(Nil)
product(zs)
zs.product

@tailrec
final def reverse[A](list: List[A], acc: List[A] = List.empty[A]): List[A] = list match {
  case Nil => acc
  case head :: tail => reverse(tail, head :: acc)
}
reverse( (1 to 10).toList )

def findNthElementFromRight[A](list: List[A], nthElement: Int): Option[A] = {
  @tailrec
  def reverse(list: List[A], acc: List[A] = List.empty[A]): List[A] = list match {
    case Nil => acc
    case head :: tail => reverse(tail, head :: acc)
  }
  Try { reverse(list)(nthElement - 1) }.toOption
}
val findElements = (1 to 10).toList
findNthElementFromRight(findElements, 4)
findNthElementFromRight(findElements, 15)

def doesListAcontainListB[A](listA: List[A], listB: List[A]): Boolean = {
  listB.count(b => listA.contains(b)) == listB.length
}
doesListAcontainListB( listA = (1 to 20).toList, listB = (5 to 15).toList )
doesListAcontainListB( listA = (15 to 50).toList, listB = (10 to 30).toList )

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

def selectByIndex(source: List[Int], index: Int): Option[Int] = {
  @tailrec
  def loop(source: List[Int], index: Int, acc: Int = 0): Option[Int] = source match {
    case Nil => None
    case head :: tail => if (acc == index) Some(head) else loop(tail, index, acc + 1)
  }
  loop(source, index)
}
val ts = 1 to 10 toList
val us = List[Int]()
val vs = List(1, 2, 3, 4)
val x = selectByIndex(ts, 5)
x.get == ts(5)
val y = selectByIndex(us, 5)
y.isEmpty == true
val z = selectByIndex(vs, 5)
z.isEmpty == true