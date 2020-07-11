trait Semigroup[A] {
  def append(x: A, y: => A): A
}

trait Monoid[A] extends Semigroup[A] {
  def zero: A
}

val addMonoid = new Monoid[Int] {
  override def zero: Int = 0
  override def append(x: Int, y: => Int): Int = x + y
}

addMonoid.zero
addMonoid.append(1, 2)