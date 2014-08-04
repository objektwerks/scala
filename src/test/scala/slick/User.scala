package slick

import scala.slick.driver.H2Driver.simple._

case class User(id: Option[Int] = None, name: String)

class Users(tag: Tag) extends Table[User](tag, "users") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.NotNull)

  def * = (id.?, name) <> (User.tupled, User.unapply)
}