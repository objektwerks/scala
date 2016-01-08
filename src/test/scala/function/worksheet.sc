def incr(n: Int): Int = n + 1
def decr(n: Int): Int = n - 1

val xs = 1 to 10 toList
val ys = xs map incr map decr
xs == ys