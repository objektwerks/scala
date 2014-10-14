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

  test("get users") {
    val users: List[User] = Store.getUsers
    assert(users.size == 1)
    print(users)
  }

  test("get user tasks by name") {
    val users: List[User] = Store.getUsers
    assert(users.size == 1)
    for (u <- users) {
      val usersWithTasks: Map[User, List[Task]] = Store.getUserTasksByName(u.name)
      assert(usersWithTasks.size == 1)
      println(usersWithTasks)
    }
  }
}