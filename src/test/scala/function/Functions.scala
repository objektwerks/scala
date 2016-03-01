package function

import function.Triangle.Triangle

import scala.annotation.tailrec

object Triangle extends Enumeration {
  type Triangle = Value
  val equilateral, isosceles, scalene = Value
}

object Functions {
  type Sides = (Int, Int, Int)

  def identifyTriangle(sides: Sides): Triangle = sides match {
    case (a, b, c) if a == b && c == b => Triangle.equilateral
    case (a, b, c) if a == b || c == b => Triangle.isosceles
    case (a, b, c) if a != b && c != b => Triangle.scalene
  }

  def selectByIndex(source: List[Int], index: Int): Option[Int] = {
    @tailrec
    def loop(source: List[Int], index: Int, acc: Int = 1): Option[Int] = source match {
      case Nil => None
      case head :: tail => if (acc == index) Some(head) else loop(tail, index, acc + 1)
    }
    loop(source, index)
  }

  def isIntersectable(source: List[Int], target: List[Int]): Boolean = {
    val result = for (s <- source if target.contains(s)) yield s
    source == result
  }
}