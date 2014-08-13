package monad

import org.scalatest.FunSuite

class ForMonad extends FunSuite {
  test("for comprehension vs map") {
    val list = List(1, 2)
    val forList = for (i <- list) yield i * 2
    val mapList = list map {i => i * 2}
    assert (forList == List(2, 4))
    assert (mapList == List(2, 4))
  }

}