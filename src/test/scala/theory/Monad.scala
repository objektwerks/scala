package theory

trait Monad[M[_]] {
  def unit[A](a: => A): M[A]

  def map[A, B](a: M[A])(f: A => B): M[B]

  def flatMap[A, B](a: M[A])(f: A => M[B]): M[B]
}

case class Identity[A](value: A) {
  def map[B](f: A => B): Identity[B] = Identity(f(value))

  def flatMap[B](f: A => Identity[B]): Identity[B] = f(value)
}