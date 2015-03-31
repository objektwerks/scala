package theory

trait Applicative[F[_]] extends Functor {
  def unit[A](a: => A): F[A]
}