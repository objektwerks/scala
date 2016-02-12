package function

import function.Triangle.Triangle
import org.scalatest.FunSuite

import scala.annotation.tailrec

object Triangle extends Enumeration {
  type Triangle = Value
  val equilateral, isosceles, scalene = Value
}

object Functions {
  def identifyTriangleType(sides: (Int, Int, Int)): Triangle = sides match {
    case (a, b, c) if a == b && a == c => Triangle.equilateral
    case (a, b, c) if a == b || a == c => Triangle.isosceles
    case (a, b, c) if a != b && a != c => Triangle.scalene
  }

  @tailrec
  def findFifthElement(xs: List[Int]): Int = xs match {
    case Nil => 0
    case head :: tail => if (head == 5) head else findFifthElement(tail)
  }

  def isSourceContainedInTarget(source: List[Int], target: List[Int]): Boolean = {
    val result = for (s <- source if target.contains(s)) yield s
    source == result
  }
}

class TWCTest extends FunSuite {
  import Functions._

  test("1") {
    val equilateral = identifyTriangleType((3, 3, 3))
    val isosceles = identifyTriangleType((3, 3, 2))
    val scalene = identifyTriangleType((3, 2, 1))
    assert(equilateral == Triangle.equilateral)
    assert(isosceles == Triangle.isosceles)
    assert(scalene == Triangle.scalene)
  }

  test("2") {
    val xs = 1 to 10 toList
    val ys = List[Int]()
    val x = findFifthElement(xs)
    val y = findFifthElement(ys)
    assert(x == 5)
    assert(y == 0)
  }

  test("3") {
    val xs = 1 to 10 toList
    val ys = 1 to 100 toList
    val x = isSourceContainedInTarget(xs, ys)
    val y = isSourceContainedInTarget(ys, xs)
    assert(x)
    assert(!y)
  }
}