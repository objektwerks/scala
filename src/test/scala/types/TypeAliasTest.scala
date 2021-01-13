package types

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class TypeAliasTest extends AnyFunSuite with Matchers {
  test("type alias") {
    type User = String
    type Age = Int
    val users =  Map[User, Age]("john" -> 21, "jane" -> 19)

    users("john") shouldEqual 21
    users("jane") shouldEqual 19
  }
}