package function

import function.Triangle.Triangle
import org.scalatest.FunSuite

import scala.annotation.tailrec

object Triangle extends Enumeration {
  type Triangle = Value
  val equilateral, isosceles, scalene = Value
}

object Functions {
  type Sides = (Int, Int, Int)

  def identifyTriangle(sides: Sides): Triangle = sides match {
    case (a, b, c) if a == b && a == c => Triangle.equilateral
    case (a, b, c) if a == b || a == c => Triangle.isosceles
    case (a, b, c) if a != b && a != c => Triangle.scalene
  }

  def selectByIndex(source: List[Int], index: Int): Int = {
    @tailrec
    def loop(source: List[Int], index: Int, acc: Int = 1): Int = source match {
      case Nil => 0
      case head :: tail => if (acc == index) head else loop(tail, index, acc + 1)
    }
    loop(source, index)
  }

  def isIntersectable(source: List[Int], target: List[Int]): Boolean = {
    val result = for (s <- source if target.contains(s)) yield s
    source == result
  }
}

class TWCTest extends FunSuite {
  import Functions._

  test("1") {
    val equilateral = identifyTriangle((3, 3, 3))
    val isosceles = identifyTriangle((3, 3, 2))
    val scalene = identifyTriangle((3, 2, 1))
    assert(equilateral == Triangle.equilateral)
    assert(isosceles == Triangle.isosceles)
    assert(scalene == Triangle.scalene)
  }

  test("2") {
    val xs = 1 to 10 toList
    val ys = List[Int]()
    val zs = List(1, 2, 3, 4)
    val x = selectByIndex(xs, 5)
    val y = selectByIndex(ys, 5)
    val z = selectByIndex(zs, 5)
    assert(x == 5)
    assert(y == 0)
    assert(z == 0)
  }

  test("3") {
    val xs = 1 to 10 toList
    val ys = 1 to 100 toList
    val x = isIntersectable(xs, ys)
    val y = isIntersectable(ys, xs)
    assert(x)
    assert(!y)
  }
}