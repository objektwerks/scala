package theory

import org.scalatest.FunSuite

trait Functor[F[_]] {
  def apply[A](x: A): F[A]

  def map[A, B](fa: F[A])(f: A => B): F[B]
}

trait Monad[F[_]] {
  def unit[A](a: => A): F[A]

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def map[A, B](fa: F[A])(f: A => B): F[B]
}

class MonadTest extends FunSuite {
  test("functor") {

  }

  test("monad") {

  }
}