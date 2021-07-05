import scala.annotation.tailrec

// O(1) - Constant Time
def constantTime(array: Array[Int]): Int = {
  array(0)
}
constantTime((1 to 10).toArray[Int])

// O(n) - Linear Time
def linearTime(array: Array[Int]): Int = {
  var sum = 0
  for (i <- array) {
    sum = sum + i
  }
  sum
}
linearTime((1 to 10).toArray[Int])

// O(log N) - Binary Search
case class Number(number: Int)
implicit def ordering: Ordering[Number] = Ordering.by(_.number)
val list = List( Number(1), Number(2), Number(3) )
list.indexOf( Number(3) )

// O(n^2) - Quadratic Time
def quadraticTime(): Array[Array[Int]] = {
  val matrix = Array.ofDim[Int](3, 3)
  for (i <- 0 to matrix.length - 1) {
    for (j <- 0 to matrix.length - 1) {
      matrix(i)(j) = j
     }
  }
  matrix
}
quadraticTime()

// O(2^N) - Exponential Time
def fibonacci(n: Long): BigInt = {
  @tailrec
  def loop(n: Long, a: Long, b: Long): BigInt = n match {
    case 0 => a
    case _ => loop(n - 1, b, a + b)
  }
  loop(n, 0, 1)
}
fibonacci(39)

// O(N!) - Factorial Time
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
intersectListsResult == intersectSdkResult