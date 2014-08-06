package slick

import scala.slick.driver.H2Driver.simple._

object Schema {
  def create() (implicit session: Session, users: TableQuery[Users], tasks: TableQuery[Tasks]) = {
    (users.ddl ++ tasks.ddl).create
    load()
  }

  def drop() (implicit session: Session, users: TableQuery[Users], tasks: TableQuery[Tasks]) = {
    (users.ddl ++ tasks.ddl).drop
  }

  private def load() (implicit session: Session, users: TableQuery[Users], tasks: TableQuery[Tasks]) = {
    session.withTransaction {
      val userId = (users returning users.map(_.id)) += User(None, "Fred")
      tasks += Task(None, userId, "Mow yard.")
      tasks += Task(None, userId, "Clean garage.")
      tasks += Task(None, userId, "Paint tool shed.")
    }
  }
}