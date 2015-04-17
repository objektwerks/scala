package slick

import slick.driver.H2Driver.api._

import scala.concurrent.Future

object Store {
  private[this] val persons = TableQuery[Persons]
  private[this] val tasks = TableQuery[Tasks]
  private[this] val db = Database.forConfig("slick")

  def createSchema(): Unit = {
    val schema = DBIO.seq( ( persons.schema ++ tasks.schema ).create )
    db.run(schema)
  }

  def dropSchema(): Unit = {
    val schema = DBIO.seq( ( persons.schema ++ tasks.schema ).drop )
    db.run(schema)
  }

  def close(): Unit = {
    db.close()
  }

  def listPersons: Future[Seq[Person]] = {
    val query = for { p <- persons } yield p
    db.run(query.result)
  }

  def listTasks(person: Person): Future[Seq[Task]] = {
    val query = for { t <- tasks if t.id === person.id } yield t
    db.run(query.result)
  }

  def create(person: Person): Future[Int] =
    db.run(persons += person)

  def create(task: Task): Future[Int] =
    db.run(tasks += task)

  def update(person: Person): Future[Int] =
    db.run(filter(person).update(person))

  def delete(person: Person): Future[Int] =
    db.run(filter(person).delete)

  private[this] def filter(person: Person): Query[Persons, Person, Seq] =
    persons.filter(person => person.id === person.id)
}