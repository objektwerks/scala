package theory

trait Applicative[F[_]] extends Functor[F] {
  def unit[A](a: => A): F[A]

  def map[A,B,C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C]

  override def map[A, B](a: F[A])(f: A => B): F[B] = {
    map(a, unit(()))((a, _) => f(a))
  }

  def traverse[A,B](as: List[A])(f: A => F[B]): F[List[B]] = {
    as.foldRight(unit(List[B]()))((a, fbs) => map(f(a), fbs)(_ :: _))
  }
}