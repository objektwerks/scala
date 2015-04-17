package slick

import slick.driver.H2Driver.api._

import scala.concurrent.Future

object Store {
  val persons = TableQuery[Persons]
  val tasks = TableQuery[Tasks]
  val createSchema = DBIO.seq( ( persons.schema ++ tasks.schema ).create )
  val dropSchema = DBIO.seq( persons.schema.drop )
  val db = Database.forConfig("slick")

  def create = db.run(createSchema)
  def drop = db.run(dropSchema)

  def listPersons: Future[Seq[Person]] = {
    val query = for ( p <- persons ) yield p
    db.run(query.result)
  }

  def create(person: Person): Future[Int] =
    try db.run(persons += person)
    finally db.close()

  def update(person: Person): Future[Int] =
    try db.run(filter(person).update(person))
    finally db.close()

  def delete(person: Person): Future[Int] =
    try db.run(filter(person).delete)
    finally db.close()

  private[this] def filter(person: Person): Query[Persons, Person, Seq] =
    persons.filter(person => person.id === person.id)
}