package slick

import org.scalatest.FunSuite

import slick.Repository._

class SlickTest extends FunSuite {
  private val store = new Store
  private val fred = "Fred"
  private val user = store.createUser(User(fred))
  store.createTask(user, "Mow yard.")

  test("get user by name") {
    val userAsOption = store.getUserByName(fred)
    assert(userAsOption.get == User(fred))
  }

  test("get users") {
    val users: List[User] = store.getUsers
    assert(users.size == 1)
  }

  test("get user tasks by name") {
    val users: List[User] = store.getUsers
    assert(users.size == 1)
    for (u <- users) {
      val usersWithTasks: Map[User, List[Task]] = store.getUserTasksByName(u.name)
      assert(usersWithTasks.size == 1)
    }
  }
}