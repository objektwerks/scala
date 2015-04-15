package slick

import org.scalatest.FunSuite

import scala.concurrent.ExecutionContext.Implicits.{global => ec}

class SlickTest extends FunSuite {
  test("users") {
    Store.create
    val users = Store.listUsers
    users onSuccess { case u => println(s"users: $users") }
    Store.drop
  }
}