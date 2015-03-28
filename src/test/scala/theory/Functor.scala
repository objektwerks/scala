package theory

trait Functor[F[_]] {
  def map[A, B](a: F[A])(f: A => B): F[B]
}

object ToListOfStrings extends Functor[List] {
  def map[A, B](xs: List[A])(f: A => B): List[B] = xs map f
}