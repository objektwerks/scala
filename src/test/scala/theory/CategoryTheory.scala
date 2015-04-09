package theory

trait Semigroup[F] {
  def append(x: F, y: F): F
}

trait Monoid[F] extends Semigroup[F] {
  def zero: F
  def isLawful(x: F, y: F, z: F): Boolean = {
    val associative = append(append(x, y), z) == append(x, append(y, z))
    val identity = append(zero, x) == x
    associative && identity
  }
}

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

trait Applicative[F[_]] extends Functor[F] {
  def pure[A](a: => A): F[A]
  def apply[A, B](fa: F[A])(f: F[A => B]): F[B]
  override def map[A, B](fa: F[A])(f: A => B): F[B] = apply(fa)(pure(f))
}

trait Monad[F[_]] extends Functor[F] {
  def pure[A](a: => A): F[A]
  def flatten[A](ffa: F[F[A]]): F[A]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

object CategoryTheory {
  val adderMonoid = new Monoid[Int] {
    override def zero: Int = 0
    override def append(x: Int, y: Int): Int = x + y
  }

  val listFunctor = new Functor[List] {
    override def map[A, B](xs: List[A])(f: A => B): List[B] = xs map f
  }

  val optionApplicative = new Applicative[Option] {
    override def pure[A](a: => A): Option[A] = Some(a)
    override def apply[A, B](fa: Option[A])(ff: Option[A => B]): Option[B] = (fa, ff) match {
      case (None, _) => None
      case (Some(a), None) => None
      case (Some(a), Some(f)) => Some(f(a))
    }
  }

  val optionMonad = new Monad[Option] {
    override def pure[A](a: => A): Option[A] = Option(a)
    override def flatten[A](ooa: Option[Option[A]]): Option[A] = ooa flatMap identity
    override def map[A, B](oa: Option[A])(f: (A) => B): Option[B] = oa map f
    override def flatMap[A, B](oa: Option[A])(f: (A) => Option[B]): Option[B] = oa flatMap f
  }

  def isIdempotent[T](op: T => T, x: T): Boolean = {
    val f = op
    val g = op compose op
    f(x) == g(x)
  }

  def isCommutative[T](op: (T, T) => T, x: T, y: T): Boolean = {
    op(x, y) == op(y, x)
  }

  def isAssociative[T](op: (T, T) => T, x: T, y: T, z: T): Boolean = {
    op(op(x, y), z) == op(x, op(y, z))
  }
}