package slick

import slick.driver.H2Driver.api._

import scala.concurrent.{ExecutionContext, Future}

object Store {
  private[this] val persons = TableQuery[Persons]
  private[this] val tasks = TableQuery[Tasks]
  private[this] val db = Database.forConfig("slick")
  private implicit val ec = ExecutionContext.global


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

  def create(person: Person): Future[Int] = {
    db.run(persons.forceInsert(person))
  }

  def create(task: Task): Future[Int] = {
    db.run(tasks.forceInsert(task))
  }

  def update(person: Person): Future[Int] = {
    db.run(filter(person).update(person))
  }

  def update(task: Task): Future[Int] = {
     db.run(filter(task).update(task))
  }

  private[this] def filter(person: Person): Query[Persons, Person, Seq] = {
    persons.filter(person => person.id === person.id)
  }

  private[this] def filter(task: Task): Query[Tasks, Task, Seq] = {
    tasks.filter(task => task.id === task.id)
  }
}