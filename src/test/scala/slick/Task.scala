package slick

import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.ForeignKeyQuery

case class Task(id: Option[Int] = None, task: String)

class Tasks(tag: Tag) extends Table[Task](tag, "tasks") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("task", O.NotNull)

  def * = (id.?, name) <> (Task.tupled, Task.unapply)

  def task: ForeignKeyQuery[Users, Task] = {
    foreignKey("user_fk", id, TableQuery[Users])(_.id)
  }
}