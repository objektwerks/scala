// Data
val data = (1 to 10).toArray[Int]

// O(n) - Linear Time
def linearTime(array: Array[Int]): Int = {
  var length = 0
  array.foreach { _ =>
    length = length + 1
  }
  length
}
linearTime(data)

// O(1) - Constant Time
def constantTime(array: Array[Int]): Int = {
  array(0)
}
constantTime(data)