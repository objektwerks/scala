val incr = (n: Int) => n + 1
val decr = (n: Int) => n - 1
val composedIncrDecr = incr compose decr
val andThenIncrDecr = incr andThen decr

val xs = 1 to 10 toList
val ys = xs map incr map decr
val zs = xs map composedIncrDecr map andThenIncrDecr
xs == ys
ys == zs
