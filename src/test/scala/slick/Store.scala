package slick

import slick.driver.H2Driver.api._

import scala.concurrent.Future

object Store {
  val users = TableQuery[Users]
  val createSchema = DBIO.seq( users.schema.create )
  val dropSchema = DBIO.seq( users.schema.drop )
  val db = Database.forConfig("slick")

  def create = db.run(createSchema)
  def drop = db.run(dropSchema)

  def listUsers: Future[Seq[User]] = {
    val q = for ( u <- users ) yield u
    val a = q.result
    db.run( a )
  }
}