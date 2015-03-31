package theory

trait Functor[F[_]] {
  def map[A, B](a: F[A])(f: A => B): F[B]
}

trait Applicative[F[_]] extends Functor[F] {
  def unit[A](a: => A): F[A]
}

object ToListOfStrings extends Functor[List] {
  override def map[A, B](xs: List[A])(f: A => B): List[B] = xs map f
}