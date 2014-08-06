package slick

import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.slick.driver.H2Driver.simple._

class SlickTest extends FunSuite with BeforeAndAfter {
  val db: Database = Database.forURL("jdbc:h2:mem:test", driver = "org.h2.Driver")
  implicit var session: Session = _
  implicit val users: TableQuery[Users] = TableQuery[Users]
  implicit val tasks: TableQuery[Tasks] = TableQuery[Tasks]

  before {
    session = db.createSession()
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
      tasks += Task(None, userId, " take a nap!")
    }
    println(users.list)
    println(tasks.list)
  }

  def query() = {
    val userTasks = for {
      (u, t) <- users innerJoin tasks
    } yield (u.name, t.task)
    println(userTasks.list)
  }
}