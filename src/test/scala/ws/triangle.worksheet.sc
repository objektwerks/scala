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
    case (x, y, z) if x == y && y == z => Triangles.equilateral
    case (x, y, z) if x == y || y == z || z == x => Triangles.isoceles
    case _ => Triangles.scalene
  }
}

val equilateral = Triangle(3, 3, 3)
val e = equilateral.kind == Triangles.equilateral

val isosceles = Triangle(3, 6, 3)
val i = isosceles.kind == Triangles.isoceles

val scalene = Triangle(3, 6, 9)
val s = scalene.kind == Triangles.scalene