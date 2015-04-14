package enumerator

import org.scalatest.FunSuite

class EnumTest extends FunSuite {
  test("enum") {
    Lights.red == Lights.red
    Lights.yellow == Lights.yellow
    Lights.green == Lights.green
  }
}