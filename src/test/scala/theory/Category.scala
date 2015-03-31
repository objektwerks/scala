package theory

trait Monoid[F] {
  def id: F

  def op(x: F, y: F): F
}

object Adder extends Monoid[Int] {
  override def id: Int = 0

  override def op(x: Int, y: Int): Int = x + y

  def fold(xs: List[Int]): Int = xs.fold(id)(op)

  def isValid(x: Int, y: Int, z: Int): Boolean = {
    val associative = op(op(x, y), z) == op(x, op(y, z))
    val identity = op(id, x) == x
    associative && identity
  }
}

trait Functor[F[_]] {
  def map[A, B](a: F[A])(f: A => B): F[B]
}

trait Applicative[F[_]] extends Functor[F] {
  def unit[A](a: => A): F[A]
}

object ToListOfStrings extends Functor[List] {
  override def map[A, B](xs: List[A])(f: A => B): List[B] = xs map f
}

trait Monad[F[_]] extends Functor[F] {
  def unit[A](a: => A): F[A]

  def flatten[A](a: F[F[A]]): F[A]

  def flatMap[A, B](a: F[A])(f: A => F[B]): F[B]
}

case class Identity[A](value: A) {
  def map[B](f: A => B): Identity[B] = Identity(f(value))

  def flatMap[B](f: A => Identity[B]): Identity[B] = f(value)
}