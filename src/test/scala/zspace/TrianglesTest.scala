package zspace

import org.scalatest.{FunSuite, Matchers}

/**
  * equilateral = all 3 sides equal
  * isosceles = 2 sides equal
  * scalene = 0 sides equal
  */
object Triangles extends Enumeration {
  type Triangles = Value
  val equilateral, isoceles, scalene = Value
}

case class Triangle(a: Int, b: Int, c: Int) {
  import Triangles._

  def kind: Triangles = (a, b, c) match {
    case (x, y, z) if x == y && y == z => equilateral
    case (x, y, z) if x == y || y == z || z == x => isoceles
    case _ => scalene
  }
}

class TrianglesTest extends FunSuite with Matchers {
  test("determine kinds of triangles") {
    val equilateral = Triangle(3, 3, 3)
    equilateral.kind shouldBe Triangles.equilateral

    val isosceles = Triangle(3, 6, 3)
    isosceles.kind shouldBe Triangles.isoceles

    val scalene = Triangle(3, 6, 9)
    scalene.kind shouldBe Triangles.scalene
  }
}