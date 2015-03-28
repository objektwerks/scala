package theory

import org.scalatest.FunSuite

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

class MonadTest extends FunSuite {
  test("monad") {

  }
}