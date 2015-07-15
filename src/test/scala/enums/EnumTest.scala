package enums

import org.scalatest.FunSuite

class EnumTest extends FunSuite {
  test("java enum") {
    Lights.red == Lights.red
    Lights.yellow == Lights.yellow
    Lights.green == Lights.green
  }
}