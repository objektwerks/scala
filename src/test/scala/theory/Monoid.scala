package theory

trait Monoid[M] {
  def id: M

  def op(x: M, y: M): M
}

object Adder extends Monoid[Int] {
  override def id: Int = 0

  override def op(x: Int, y: Int): Int = x + y

  def fold(xs: List[Int]): Int = xs.fold(id)(op)

  def isValid(x: Int, y: Int, z: Int): Boolean = {
    val associative = op(op(x, y), z) == op(x, op(y, z))
    val identity = op(id, x) == x
    associative && identity
  }
}