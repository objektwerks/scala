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