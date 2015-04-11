package enumerator

import org.scalatest.FunSuite

class EnumeratorTest extends FunSuite {
  test("enumerator") {
    Lights.red == Lights.red
    Lights.yellow == Lights.yellow
    Lights.green == Lights.green
  }
}