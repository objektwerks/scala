package theory

trait Monad[F[_]] extends Functor[F]{
  def unit[A](a: => A): F[A]
  
  def flatMap[A, B](a: F[A])(f: A => F[B]): F[B]
}

case class Identity[A](value: A) {
  def map[B](f: A => B): Identity[B] = Identity(f(value))

  def flatMap[B](f: A => Identity[B]): Identity[B] = f(value)
}