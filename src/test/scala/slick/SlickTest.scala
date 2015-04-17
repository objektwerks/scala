package slick

import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.concurrent.ExecutionContext

class SlickTest extends FunSuite with BeforeAndAfterAll {
  private implicit val ec = ExecutionContext.global

  override protected def beforeAll(): Unit = {
    super.beforeAll()
    Store.createSchema()
  }

  override protected def afterAll(): Unit = {
    super.afterAll()
    Store.dropSchema()
    Store.close()
  }

  test("persons 1 -> * tasks") {
    val future = Store.listPersons
    future onSuccess { case p => p foreach println }
  }
}