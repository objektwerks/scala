package slick

import scala.slick.driver.H2Driver.simple._

case class Task(id: Int = 0, userName: String, task: String)

class Tasks(tag: Tag) extends Table[Task](tag, "tasks") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def userName: Column[String] = column[String]("user_name")

  def task = column[String]("task", O.NotNull)

  def * = (id, userName, task) <> (Task.tupled, Task.unapply)

  def user = foreignKey("user_fk", userName, TableQuery[Users])(_.name)
}