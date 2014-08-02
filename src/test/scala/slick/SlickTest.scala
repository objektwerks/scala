package slick

import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.slick.driver.H2Driver.simple._

class SlickTest extends FunSuite with BeforeAndAfter {
  implicit var session: Session = _

  before {
    session = Database.forURL("jdbc:h2:mem:test", driver = "org.h2.Driver").createSession()
  }

  after {
    session.close()
  }

  test("test") {

  }
}