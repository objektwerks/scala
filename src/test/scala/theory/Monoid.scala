package theory

trait Monoid[A] {
  def op(a1: A, a2: A): A

  def id: A
}

object Adder extends Monoid[Int] {
  override def op(x: Int, y: Int): Int = x + y

  override def id: Int = 0

  def fold(xs: List[Int]): Int = xs.fold(id)(op)

  def isValid(x: Int, y: Int, z: Int): Boolean = {
    val associative = op(op(x, y), z) == op(x, op(y, z))
    val identity = op(id, x) == x
    associative && identity
  }
}