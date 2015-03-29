package theory

trait Monad[M[_]] {
  def unit[A](a: => A): M[A]

  def map[A, B](a: M[A])(f: A => B): M[B]

  def flatMap[A, B](a: M[A])(f: A => M[B]): M[B]
}

object ListMonad extends Monad[List] {
  override def unit[A](a: => A): List[A] = List(a)

  override def flatMap[A, B](a: List[A])(f: (A) => List[B]): List[B] = a flatMap f

  override def map[A, B](a: List[A])(f: (A) => B): List[B] = a map f
}