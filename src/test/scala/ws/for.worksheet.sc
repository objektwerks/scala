val xs = List(1, 2, 3)

for {
  x <- xs
} yield x * 2

for {
  x <- xs
  if x > 2 // if guard
} yield x * 3