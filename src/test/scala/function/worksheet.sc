val incr = (n: Int) => n + 1
val decr = (n: Int) => n - 1
val incrdecr = incr compose decr

val xs = 1 to 10 toList
val ys = xs map incr map decr
val zs = xs map incrdecr
xs == ys
ys == zs
