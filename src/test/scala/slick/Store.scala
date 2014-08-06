package slick

object Store {
  import scala.slick.driver.H2Driver.simple._

  val db: Database = Database.forURL("jdbc:h2:mem:test", driver = "org.h2.Driver")
  implicit var session: Session = _
  implicit val users: TableQuery[Users] = TableQuery[Users]
  implicit val tasks: TableQuery[Tasks] = TableQuery[Tasks]

  def open() = {
    session = db.createSession()
    Schema.create()
  }

  def close() = {
    Schema.drop()
    session.close()
  }

  def listUsers(): List[User] = {
    users.list
  }

  def findUserById(id: Int): Map[User, List[Task]] = {
    val query = for {
      (u, t) <- users leftJoin tasks on(_.id === _.userId)
    } yield (u, t)
    val list = query.list
    val key = list.head._1
    val values = list.map(_._2).toList
    Map(key -> values)
  }
}