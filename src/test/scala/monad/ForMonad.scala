package monad

import org.scalatest.FunSuite

class ForMonad extends FunSuite {
  test("for > map") {
    val xs = List(1, 2)
    val forList = for (x <- xs) yield x * 2
    val mapList = xs map { x => x * 2 }
    assert(forList == List(2, 4))
    assert(mapList == List(2, 4))
  }

  test("for > flatmap > map") {
    val xs = List(2, 4)
    val ys = List (3, 5)
    val forList = for (x <- xs; y <- ys) yield x * y
    val mapList = xs flatMap { e => ys map { o => e * o }}
    assert (forList == List (2 * 3, 2 * 5, 4 * 3, 4 * 5))
    assert (mapList == List (2 * 3, 2 * 5, 4 * 3, 4 * 5))
  }

  test("for > flatmap > flatmap > map") {
    val xs = List(2, 4)
    val ys = List (3, 5)
    val zs = List(1, 6)
    val forList = for (x <- xs; y <- ys; z <- zs) yield x * y * z
    val mapList = xs flatMap { x => ys flatMap { y => { zs map { z => x * y * z }}}}
    assert(forList == List(6, 36, 10, 60, 12, 72, 20, 120))
    assert(mapList == List(6, 36, 10, 60, 12, 72, 20, 120))
  }
}