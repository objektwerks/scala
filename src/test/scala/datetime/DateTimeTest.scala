package datetime

import java.time._
import java.time.temporal.ChronoUnit

import org.scalatest.{FunSuite, Matchers}

class DateTimeTest extends FunSuite with Matchers {
  test("local date") {
    val d1 = LocalDate.of(2014, 1, 1)
    val d2 = LocalDate.of(2014, Month.JANUARY, 1)
    val d3 = LocalDate.ofYearDay(2014, 1)
    d1 shouldEqual d2
    d1 shouldEqual d3
    d2 shouldEqual d3
    !d1.isLeapYear shouldBe true
    d1.lengthOfMonth shouldEqual 31
    d1.lengthOfYear shouldEqual 365
    d1.plusDays(12).getDayOfMonth shouldEqual 13
    d1.minusDays(1).getDayOfMonth shouldEqual 31
    d1.plusMonths(2).getMonthValue shouldEqual 3
    d1.minusMonths(1).getMonth shouldEqual Month.DECEMBER
    d1.plusYears(1).getYear shouldEqual 2015
    d1.minusYears(1).getYear shouldEqual 2013
    d1.withDayOfMonth(3).getDayOfMonth shouldEqual 3
    d1.withMonth(3).getMonthValue shouldEqual 3
    d1.withYear(2013).getYear shouldEqual 2013
    LocalDate.of(2014, 1, 3).isAfter(LocalDate.of(2014, 1, 1))
    LocalDate.of(2014, 1, 1).isBefore(LocalDate.of(2014, 1, 3))
    d1 shouldEqual LocalDate.parse("2014-01-01")
  }

  test("local time") {
    val t1 = LocalTime.of(3, 3)
    val t2 = LocalTime.of(3, 3, 0)
    val t3 = LocalTime.of(3, 3, 0, 0)
    t1 shouldEqual t2
    t1 shouldEqual t3
    t2 shouldEqual t3
    t1.plusHours(6).getHour shouldEqual 9
    t1.minusHours(2).getHour shouldEqual 1
    t1.plusMinutes(30).getMinute shouldEqual 33
    t1.minusMinutes(2).getMinute shouldEqual 1
    t1.withHour(9).getHour shouldEqual 9
    t1.withMinute(33).getMinute shouldEqual 33
    t1 shouldEqual LocalTime.parse("03:03:00")
  }

  test("time zone") {
    val cst = ZonedDateTime.now(ZoneId.of("US/Central"))
    val est = ZonedDateTime.now(ZoneId.of("US/Eastern"))
    est.getHour - cst.getHour shouldEqual 1
  }

  test("period") {
    val p = Period.of(3, 3, 3)
    val d = LocalDate.of(2013, 3, 3).plus(p)
    d shouldEqual LocalDate.parse("2016-06-06")
  }

  test("duration") {
    val d = Duration.of(33, ChronoUnit.SECONDS)
    val t = LocalTime.of(3, 3, 0).plus(d)
    t shouldEqual LocalTime.parse("03:03:33")
  }
}