package slick

import slick.driver.H2Driver.api._

case class User(id: Option[Int], name: String)

class Users(tag: Tag) extends Table[User](tag, "users") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def * = (id.?, name) <> (User.tupled, User.unapply)
}