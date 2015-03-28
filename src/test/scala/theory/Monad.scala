package theory

trait Monad[T[_]] {
  def unit[A](a: => A): T[A]

  def flatMap[A, B](ma: T[A])(f: A => T[B]): T[B]

  def map[A, B](a: T[A])(f: A => B): T[B]
}