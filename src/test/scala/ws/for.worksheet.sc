val xs = List(1, 2, 3)

xs.filter(_ > 2)

for {
  x <- xs
} yield x * 2

for {
  x <- xs
  if x > 2
} yield x * 3