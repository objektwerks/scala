package theory

import org.scalatest.FunSuite

class MonadTest extends FunSuite {
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