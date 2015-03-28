package theory

trait Monad[M[_]] {
  def unit[A](a: => A): M[A]

  def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]

  def map[A, B](a: M[A])(f: A => B): M[B]
}