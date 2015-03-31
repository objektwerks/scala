package theory

import org.scalatest.FunSuite

class MonadTest extends FunSuite {
  test("option monad") {
    val optionMonad = new Monad[Option] {
      override def unit[A](a: => A): Option[A] = Some(a)
      override def compose[A, B, C](f: (A) => Option[B], g: (B) => Option[C]): (A) => Option[C] = ???
      override def flatten[A](f: Option[Option[A]]): Option[A] = f flatten
      override def map[A, B](a: Option[A])(f: (A) => B): Option[B] = a map f
      override def flatMap[A, B](a: Option[A])(f: (A) => Option[B]): Option[B] = a flatMap f
    }
    val option: Option[Int] = optionMonad.unit(1)
    val mappedOption: Option[Int] = option.map(i => i * 3)
    val flatMappedOption: Option[Int] = option.flatMap(i => Some(i))
    assert(option.get == 1)
    assert(mappedOption.get == 3)
    assert(flatMappedOption.get == 1)
    assert(option != mappedOption)
    assert(option == flatMappedOption)
  }

  test("identity monad") {
    val identity: Identity[Int] = Identity(1)
    val mappedIdentity: Identity[Int] = identity.map(i => i * 3)
    val flatMappedIdentity: Identity[Int] = identity.flatMap { i => Identity(i) }
    assert(mappedIdentity.value == 3)
    assert(flatMappedIdentity.value == 1)
    assert(identity != mappedIdentity)
    assert(identity == flatMappedIdentity)
  }
}