package slick

import org.scalatest.{BeforeAndAfterAll, FunSuite}

class SlickTest extends FunSuite with BeforeAndAfterAll {
  val name = "Fred"

  override protected def beforeAll(): Unit = {
    super.beforeAll()
    Store.open()
    val user = Store.createUser(User(name))
    Store.createTask(user, "Mow yard.")
  }

  override protected def afterAll(): Unit = {
    super.afterAll()
    Store.close()
  }

  test("get user by name") {
    val userAsOption = Store.getUserByName(name)
    assert(userAsOption.get == User(name))
  }

  test("get users") {
    val users: List[User] = Store.getUsers
    assert(users.size == 1)
  }

  test("get user tasks by name") {
    val users: List[User] = Store.getUsers
    assert(users.size == 1)
    for (u <- users) {
      val usersWithTasks: Map[User, List[Task]] = Store.getUserTasksByName(u.name)
      assert(usersWithTasks.size == 1)
    }
  }
}