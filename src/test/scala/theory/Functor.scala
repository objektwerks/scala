package theory

trait Functor[T[_]] {
  def map[A, B](a: T[A])(f: A => B): T[B]
}

object ToListOfStrings extends Functor[List] {
  override def map[A, B](xs: List[A])(f: A => B): List[B] = xs map f
}