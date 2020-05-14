case class Writer[A](value: A, log: List[String]) {
  def map[B](f: A => B): Writer[B] = Writer(f(value), log)

  def flatMap[B](f: A => Writer[B]): Writer[B] = {
    val w = f(value)
    val v = w.value
    val l = log ::: w.log
    Writer(v, l)
  }
}

def f(i: Int): Writer[Int] = {
  val v = i * 2
  val l = List(s"f: $i * 2 = $v")
  Writer(v, l)
}

def g(i: Int): Writer[Int] = {
  val v = i * 3
  val l = List(s"g: $i * 3 = $v")
  Writer(v, l)
}

def h(i: Int): Writer[Int] = {
  val v = i * 4
  val l = List(s"h: $i * 4 = $v")
  Writer(v, l)
}

val writer = for {
  fr <- f(100)
  gr <- g(fr)
  hr <- h(gr)
} yield hr

val compiler = f(100).flatMap { fr => g(fr).flatMap { gr => h(gr).map { hr => hr } } }
