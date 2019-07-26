package function

import org.scalatest.{FunSuite, Matchers}

/**
  * Write a function that takes three sides of a triangle and answers if it's equilateral, isosceles, or scalene.
  * equilateral = all 3 sides equal
  * isosceles = 2 sides equal
  * scalene = 0 sides equal
  */
case class Triangle(a: Int, b: Int, c: Int) {
  import Triangles._

  def kind: Triangles = (a, b, c) match {
    case (x, y, z) if x == y && y == z => equilateral
    case (x, y, z) if x == y || y == z || z == x => isoceles
    case _ => scalene
  }
}

object Triangles extends Enumeration {
  type Triangles = Value
  val equilateral, isoceles, scalene = Value
}

class TrianglesTest extends FunSuite with Matchers {
  test("determine kinds of triangles") {
    val equilateral = function.Triangle(3, 3, 3)
    equilateral.kind shouldBe Triangles.equilateral

    val isosceles = function.Triangle(3, 6, 3)
    isosceles.kind shouldBe Triangles.isoceles

    val scalene = function.Triangle(3, 6, 9)
    scalene.kind shouldBe Triangles.scalene
  }
}