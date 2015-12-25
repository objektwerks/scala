for {
  x <- Seq(1, 2, 3)
  y <- Seq(4, 5, 6)
} yield x + y

val a = Set(1, 2, 3,4, 5, 6)
val b = Set(3, 4, 7, 8, 9, 10)

a.intersect(b)

a.union(b)

a.diff(b)