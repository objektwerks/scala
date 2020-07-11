val as = List(1, 2, 3)

val filter = as.filter(_ > 2)

val map = for {
  a <- as
} yield a * 2

val withFilter = for {
  a <- as
  if a > 2
} yield a * 3