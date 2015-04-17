package slick

import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.concurrent.ExecutionContext.Implicits.{global => ec}

class SlickTest extends FunSuite with BeforeAndAfterAll {
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
    val persons = Store.listPersons
    persons onSuccess { case p => println(s"Persons: $persons") }
  }
}