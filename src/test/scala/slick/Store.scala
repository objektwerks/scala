package slick

object Store {
  import scala.slick.driver.H2Driver.simple._

  val db: Database = Database.forURL("jdbc:h2:mem:test", driver = "org.h2.Driver")
  implicit var session: Session = _
  implicit val users: TableQuery[Users] = TableQuery[Users]
  implicit val tasks: TableQuery[Tasks] = TableQuery[Tasks]

  def listUsers(): List[User] = {
    users.list
  }

  def findUserById(id: Int): List[(String, String)] = {
    val query = for {
      (u, t) <- users leftJoin tasks on(_.id === _.userId)
    } yield (u.name, t.task)
    query.list
  }


  def open() = {
    session = db.createSession()
    createSchema()
    loadEntities()
  }

  def close() = {
    dropSchema()
    session.close()
  }

  private def createSchema() = {
    (users.ddl ++ tasks.ddl).create
  }

  private def dropSchema() = {
    (users.ddl ++ tasks.ddl).drop
  }

  private def loadEntities() = {
    session.withTransaction {
      val userId = (users returning users.map(_.id)) += User(None, "Fred")
      tasks += Task(None, userId, "Mow yard.")
      tasks += Task(None, userId, "Clean garage.")
      tasks += Task(None, userId, "Paint tool shed.")
    }
  }
}