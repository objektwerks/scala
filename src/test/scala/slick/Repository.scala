package slick

import slick.driver.H2Driver.api._

case class Person(id: Int, name: String, age: Int)

class Persons(tag: Tag) extends Table[Person](tag, "persons") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def age = column[Int]("age")
  def * = (id, name, age) <> (Person.tupled, Person.unapply)
}