package slick

import org.scalatest.FunSuite

import scala.concurrent.ExecutionContext.Implicits.{global => ec}

class SlickTest extends FunSuite {
  test("users") {
    Store.create
    val persons = Store.listPersons
    persons onSuccess { case u => println(s"Persons: $persons") }
    Store.drop
  }
}