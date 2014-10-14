package slick

import scala.slick.driver.H2Driver.simple._

case class User(name: String)

class Users(tag: Tag) extends Table[User](tag, "users") {
  def name = column[String]("name", O.NotNull)

  def * = name <> (User.apply, User.unapply)
}