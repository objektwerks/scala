package slick

import org.scalatest.{BeforeAndAfterAll, FunSuite}

class SlickTest extends FunSuite with BeforeAndAfterAll {
  override protected def beforeAll(): Unit = {
    super.beforeAll()
    Store.open()
  }

  override protected def afterAll(): Unit = {
    super.afterAll()
    Store.close()
  }

  test("list users") {
    val users: List[User] = Store.listUsers()
    assert(users.size == 1)
    for (u <- users) {
      assert(u.id > 0)
      assert(u.name.length > 0)
    }
  }

  test("find user by id") {
    val users: List[User] = Store.listUsers()
    assert(users.size == 1)
    for (u <- users) {
      val usersWithTasks: Map[User, List[Task]] = Store.findUserById(u.id)
      assert(usersWithTasks.size == 1)
      println(usersWithTasks)
    }
  }
}