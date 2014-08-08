package slick

import scala.slick.driver.H2Driver.simple._

case class Task(id: Int = 0, userId: Int, task: String)

class Tasks(tag: Tag) extends Table[Task](tag, "tasks") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def userId: Column[Int] = column[Int]("user_id")

  def task = column[String]("task", O.NotNull)

  def * = (id, userId, task) <> (Task.tupled, Task.unapply)

  def user = foreignKey("user_fk", userId, TableQuery[Users])(_.id)
}