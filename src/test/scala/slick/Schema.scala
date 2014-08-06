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
      val userId = (users returning users.map(_.id)) += User(0, "Fred")
      tasks += Task(0, userId, "Mow yard.")
      tasks += Task(0, userId, "Clean garage.")
      tasks += Task(0, userId, "Paint tool shed.")
    }
  }
}