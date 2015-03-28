package theory

trait Monad[M[_]] {
  def unit[A](a: A): M[A]

  def map[A, B](f: A => B): M[A] => M[B]

  def flatten[A](m: M[M[A]]): M[A]

  def flatMap[A, B](m: M[A])(f: A => M[B]): M[B]

}

object ListMonad extends Monad[List] {
  override def unit[A](a: A) = a :: Nil

  override def map[A,B](f: A => B) = (xs: List[A]) => xs map f

  override def flatten[A](xs: List[List[A]]) = xs flatten

  override def flatMap[A, B](xs: List[A])(f: (A) => List[B]): List[B] = xs map f flatten
}