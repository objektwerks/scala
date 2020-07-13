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

// O(2^N) - Exponential Times
@tailrec
final def factorial(n: Int, acc: Int = 1): Int = n match {
  case i if i < 1 => acc
  case _ => factorial(n - 1, acc * n)
}
factorial(9)