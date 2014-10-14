package slick

import scala.slick.driver.H2Driver.simple._

object Store {
  val db = Database.forURL("jdbc:h2:mem:slick", driver = "org.h2.Driver")
  implicit var session = db.createSession()
  val users = TableQuery[Users]
  val tasks = TableQuery[Tasks]

  def open() = {
    (users.ddl ++ tasks.ddl).create
    session.withTransaction {
      val userId = (users returning users.map(_.id)) += User(0, "Fred")
      tasks += Task(0, userId, "Mow yard.")
      tasks += Task(0, userId, "Clean garage.")
      tasks += Task(0, userId, "Paint tool shed.")
    }
  }

  def close() = {
    (users.ddl ++ tasks.ddl).drop
    session.close()
  }

  def listUsers(): List[User] = {
    users.list
  }

  def findUserById(id: Int): Map[User, List[Task]] = {
    val query = for {
      (u, t) <- users leftJoin tasks on(_.id === _.userId)
    } yield (u, t)
    val list: List[(User, Task)] = query.list
    val key: User = list.head._1
    val values: List[Task] = list.map(_._2).toList
    val map: Map[User, List[Task]] = Map(key -> values)
    map
  }
}