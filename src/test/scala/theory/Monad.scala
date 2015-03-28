package theory

trait Monad[M[_]] {
  def unit[A](a: => A): M[A]

  def map[A, B](a: M[A])(f: A => B): M[B]

  def flatMap[A, B](a: M[A])(f: A => M[B]): M[B]
}

class Bento extends Monad[List[String]] {
  override def unit[A](a: => A): List[A] = a :: Nil

  override def map[A, B](a: List[A])(f: (A) => B): List[B] = a map f

  override def flatMap[A, B](a: List[A])(f: (A) => List[B]): List[B] = a flatmap f
}