package enums

import org.scalatest.FunSuite

object WeekDay extends Enumeration {
  type WeekDay = Value
  val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
}

class EnumTest extends FunSuite {
  test("java enum") {
    assert(Lights.red == Lights.red)
    assert(Lights.yellow == Lights.yellow)
    assert(Lights.green == Lights.green)
  }

  test("scala enum") {
    assert(WeekDay.values.contains(WeekDay.Mon))
    assert(WeekDay.values.contains(WeekDay.Tue))
    assert(WeekDay.values.contains(WeekDay.Wed))
    assert(WeekDay.values.contains(WeekDay.Thu))
    assert(WeekDay.values.contains(WeekDay.Fri))
    assert(WeekDay.values.contains(WeekDay.Sat))
    assert(WeekDay.values.contains(WeekDay.Sun))
  }
}