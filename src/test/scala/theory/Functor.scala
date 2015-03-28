package theory

trait Functor[F[_]] {
  def apply[A](x: A): F[A]

  def map[A, B](fa: F[A])(f: A => B): F[B]
}