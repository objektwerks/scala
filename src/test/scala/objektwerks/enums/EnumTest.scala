package objektwerks.enums

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.SortedMap

object Weekday extends Enumeration {
  val Mon = Value(1, "Mon")
  val Tue = Value(2, "Tue")
  val Wed = Value(3, "Wed")
  val Thu = Value(4, "Thu")
  val Fri = Value(5, "Fri")
  val Sat = Value(6, "Sat")
  val Sun = Value(7, "Sun")
  val map = SortedMap[Int, Value](Mon.id -> Mon, Tue.id -> Tue, Wed.id -> Wed, Thu.id -> Thu, Fri.id -> Fri, Sat.id -> Sat, Sun.id -> Sun)
  def validate(weekday: Weekday.Value): Boolean = values.contains(weekday)
}

object Month extends Enumeration {
  val Jan = Value(1, "Jan")
  val Feb = Value(2, "Feb")
  val Mar = Value(3, "Mar")
  val Apr = Value(4, "Apr")
  val May = Value(5, "May")
  val Jun = Value(6, "Jun")
  val Jul = Value(7, "Jul")
  val Aug = Value(8, "Aug")
  val Sep = Value(9, "Sep")
  val Oct = Value(10, "Oct")
  val Nov = Value(11, "Nov")
  val Dec = Value(12, "Dec")
  val map = SortedMap[Int, Value](Jan.id -> Jan, Feb.id -> Feb, Mar.id -> Mar, Apr.id -> Apr, May.id -> May, Jun.id -> Jun,
                                  Jul.id -> Jul, Aug.id -> Aug, Sep.id -> Sep, Oct.id -> Oct, Nov.id -> Nov, Dec.id -> Dec)
  def validate(month: Month.Value): Boolean = values.contains(month)
}

class EnumTest extends AnyFunSuite with Matchers {
  test("scala enum") {
    Weekday.values.foreach(weekday => Weekday.validate(weekday) shouldBe true)
    Weekday.values.foreach(println)
    Weekday.map.foreach(println)

    Month.values.foreach(month => Month.validate(month) shouldBe true)
    Month.values.foreach(println)
    Month.map.foreach(println)
  }

  test("java enum") {
    Light.valueOf("green") shouldEqual Light.green
    Light.valueOf("yellow") shouldEqual Light.yellow
    Light.valueOf("red") shouldEqual Light.red
  }
}