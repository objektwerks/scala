// Data
val xs = (1 to 100).toArray[Int]

// O(n) - Linear Time
def linearTime(array: Array[Int]): Int = {
  array.foreach { i =>
    require(i > -1)
  }
  array.length
}

linearTime(xs)

// O(1) - Constant Time
def constantTime(array: Array[Int]): Int = {
  array(0)
}

constantTime(xs)