package theory

trait Functor[F[_]] {
  def map[A, B](a: F[A])(f: A => B): F[B]
}

trait Applicative[F[_]] extends Functor[F] {
  def unit[A](a: => A): F[A]
  def apply[A, B](f: F[A => B]): F[A] => F[B]
}

trait Monad[F[_]] extends Functor[F] {
  def unit[A](a: => A): F[A]
  def flatten[A](a: F[F[A]]): F[A]
  def flatMap[A, B](a: F[A])(f: A => F[B]): F[B]
}

trait Monoid[F] {
  def id: F
  def op(x: F, y: F): F
}