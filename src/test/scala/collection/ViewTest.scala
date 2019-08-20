package collection

import org.scalatest.{FunSuite, Matchers}

class ViewTest extends FunSuite with Matchers {
  test("view") {
    val vector = Vector(1, 2, 3)
    val view = vector.view
    view.map(_ + 1).map(_ * 2).to(Vector) shouldEqual Vector(4, 6, 8)
  }
}