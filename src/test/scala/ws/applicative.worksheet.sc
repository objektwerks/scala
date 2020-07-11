trait Applicative[F[_]] {
  def point[A](a: => A): F[A]
  def apply[A, B](fa: F[A])(f: F[A => B]): F[B]
  def map[A, B](fa: F[A])(f: A => B): F[B] = apply(fa)(point(f))
}

val optionApplicative = new Applicative[Option] {
  override def point[A](a: => A): Option[A] = Some(a)
  override def apply[A, B](fa: Option[A])(ff: Option[A => B]): Option[B] = (fa, ff) match {
    case (None, _) => None
    case (Some(_), None) => None
    case (Some(a), Some(f)) => Some(f(a))
  }
}

val point = optionApplicative.point(1)
val map = optionApplicative.map(point)(i => i * 3)
