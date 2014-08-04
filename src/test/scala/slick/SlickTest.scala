package slick

import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.slick.driver.H2Driver.simple._

class SlickTest extends FunSuite with BeforeAndAfter {
  implicit var session: Session = _
  implicit val users: TableQuery[Users] = TableQuery[Users]
  implicit val tasks: TableQuery[Tasks] = TableQuery[Tasks]

  before {
    session = Database.forURL("jdbc:h2:mem:test", driver = "org.h2.Driver").createSession()
  }

  after {
    session.close()
  }

  test("slick") {
    (users.ddl ++ tasks.ddl).create
    insert()
    query()
  }

  def insert() = {
    session.withTransaction {
      val userId = (users returning users.map(_.id)) += User(None, "Fred")
      assert(userId == 1)
      val taskId = (tasks returning tasks.map(_.id)) += Task(None, userId, "Take a nap!")
      assert(taskId == 1)
      println(users.list)
      println(tasks.list)
    }
  }

  def query() = {
    session.withTransaction {
      val userTasks = for {
        (u, t) <- users innerJoin tasks
      } yield (u.name, t.task)
      println(userTasks.list)
    }
  }
}