package slick

import org.scalatest.FunSuite

import scala.slick.driver.H2Driver.simple._

class SlickTest extends FunSuite {
  test("test") {
    val users = TableQuery[Users]
    val tasks = TableQuery[Tasks]

    Database.forURL("jdbc:h2:mem:test", driver = "org.h2.Driver") withSession { implicit session =>
      (users.ddl ++ tasks.ddl).create
      val userId = (users returning users.map(_.id)) += User(None, "Fred")
      assert(userId == 1)
      val taskId = (tasks returning tasks.map(_.id)) += Task(None, userId, "Take a nap!")
      assert(taskId == 1)
      println(users.list)
      println(tasks.list)
      val userTasks = for {
        (u, t) <- users innerJoin tasks
      } yield (u.name, t.task)
      println(userTasks.list)
    }
  }
}