package classes

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

sealed trait Email {
  def address: String
}
object Email {
    def validate(newAddress: String): Option[Email] =
      if (newAddress.nonEmpty) // hardcore email validation ;)
        Some( 
          new Email {
            override def address: String = newAddress
          } 
        )
      else None
}

sealed abstract case class Xmail private (address: String)
object Xmail {
  def validate(newAddress: String): Option[Xmail] =
    if (newAddress.nonEmpty) Some( new Xmail(newAddress){} )
    else None
}

/**
  * See: https://tuleism.github.io/blog/2020/scala-smart-constructors/
  *
  */
class SmartConstructorTest extends AnyFunSuite with Matchers {
  test("trait") {
    Email.validate("test@test.com").nonEmpty shouldBe true
    Email.validate("").isEmpty shouldBe true
  }

  test("abstract case class private") {
    Xmail.validate("test@test.com").nonEmpty shouldBe true
    Xmail.validate("").isEmpty shouldBe true
  }
}