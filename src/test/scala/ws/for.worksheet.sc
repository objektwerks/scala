val as = List(1, 2, 3)

as.filter(_ > 2)

for {
  a <- as
} yield a * 2

for {
  a <- as
  if a > 2
} yield a * 3