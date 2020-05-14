val as = List(1, 2, 3)
val foreach = as.foreach(println)

val filter = as.filter(_ > 2)

val map = for {
  a <- as
} yield a * 2

val withFilter = for {
  a <- as
  if a > 2
} yield a * 3

val xs = List(1, 2, 3)
val ys = List(3, 4, 5)
val flatMap = for {
  x <- xs
  if x % 2 == 0
  y <- ys
  if y % 2 != 0
} yield x + y
