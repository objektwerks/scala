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

  def identify(sides: Sides): Triangle = sides match {
    case (a, b, c) if a == b && a == c => Triangle.equilateral
    case (a, b, c) if a == b || a == c => Triangle.isosceles
    case (a, b, c) if a != b && a != c => Triangle.scalene
  }

  @tailrec
  def select(xs: List[Int], index: Int = 5, acc: Int = 1): Int = xs match {
    case Nil => 0
    case head :: tail => if (acc == index) head else select(tail, index, acc + 1)
  }

  def intersect(source: List[Int], target: List[Int]): Boolean = {
    val result = for (s <- source if target.contains(s)) yield s
    source == result
  }
}

class TWCTest extends FunSuite {
  import Functions._

  test("1") {
    val equilateral = identify((3, 3, 3))
    val isosceles = identify((3, 3, 2))
    val scalene = identify((3, 2, 1))
    assert(equilateral == Triangle.equilateral)
    assert(isosceles == Triangle.isosceles)
    assert(scalene == Triangle.scalene)
  }

  test("2") {
    val xs = 1 to 10 toList
    val ys = List[Int]()
    val zs = List(1, 2, 3, 4)
    val x = select(xs)
    val y = select(ys)
    val z = select(zs)
    assert(x == 5)
    assert(y == 0)
    assert(z == 0)
  }

  test("3") {
    val xs = 1 to 10 toList
    val ys = 1 to 100 toList
    val x = intersect(xs, ys)
    val y = intersect(ys, xs)
    assert(x)
    assert(!y)
  }
}