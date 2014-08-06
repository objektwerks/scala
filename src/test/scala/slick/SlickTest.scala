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
    val users: List[User] = Store.listUsers()
    assert(users.size == 1)
    for (u <- users) {
      assert(u.id.get > 0)
      assert(u.name.length > 0)
    }
  }

  test("find user by id") {
    val users: List[User] = Store.listUsers()
    for (u <- users) {
      assert(Store.findUserById(u.id.get).size == 3)
    }
  }
}