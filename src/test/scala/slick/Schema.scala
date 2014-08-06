package slick

import scala.slick.driver.H2Driver.simple._

object Schema {
  def create() (implicit session: Session) = {
    (Store.users.ddl ++ Store.tasks.ddl).create
    load()
  }

  def drop() (implicit session: Session) = {
    (Store.users.ddl ++ Store.tasks.ddl).drop
  }

  private def load() (implicit session: Session) = {
    val users = Store.users
    val tasks = Store.tasks
    session.withTransaction {
      val userId = (users returning users.map(_.id)) += User(None, "Fred")
      tasks += Task(None, userId, "Mow yard.")
      tasks += Task(None, userId, "Clean garage.")
      tasks += Task(None, userId, "Paint tool shed.")
    }
  }
}