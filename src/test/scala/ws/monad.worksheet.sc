import scala.language.higherKinds

trait Monad[F[_]] {
  def point[A](a: => A): F[A]
  def map[A, B](fa: F[A])(f: A => B): F[B]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

val optionMonad = new Monad[Option] {
  override def point[A](a: => A): Option[A] = Option(a)
  override def map[A, B](oa: Option[A])(f: A => B): Option[B] = oa map f
  override def flatMap[A, B](oa: Option[A])(f: A => Option[B]): Option[B] = oa flatMap f
}

val point = optionMonad.point(1)
val map = optionMonad.map(point)(i => i * 3)
val flatMap = optionMonad.flatMap(point)(i => Some(i))

case class Value[A](private val a: A) {
  def flatMap[B](f: A => Value[B]): Value[B] = f(a)
  def map[B](f: A => B):Value[B] = flatMap(x => Value(f(x)))
  def get: A = a
}

val one = Value(1)
val two = Value(2)
val three = Value(3)
val sum = for {
  i <- one
  j <- two
  k <- three
} yield i + j + k
val six = sum.get
