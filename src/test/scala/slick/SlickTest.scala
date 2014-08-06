package slick

import org.scalatest.{BeforeAndAfter, FunSuite}

class SlickTest extends FunSuite with BeforeAndAfter {
  before {
    Store.open()
  }

  after {
    Store.close()
  }

  test("list users") {
    assert(Store.listUsers().size == 1)
  }

  test("find user by id") {
    val users: List[User] = Store.listUsers()
    for (u <- users) {
      assert(Store.findUserById(u.id.get).size == 3)
    }
  }
}