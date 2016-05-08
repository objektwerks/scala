package datetime

import java.time._
import java.time.temporal.ChronoUnit

import org.scalatest.FunSuite

class DateTimeTest extends FunSuite {
  test("local date") {
    val d1 = LocalDate.of(2014, 1, 1)
    val d2 = LocalDate.of(2014, Month.JANUARY, 1)
    val d3 = LocalDate.ofYearDay(2014, 1)
    assert(d1 == d2)
    assert(d1 == d3)
    assert(d2 == d3)
    assert(!d1.isLeapYear)
    assert(d1.lengthOfMonth == 31)
    assert(d1.lengthOfYear == 365)
    assert(d1.plusDays(12).getDayOfMonth == 13)
    assert(d1.minusDays(1).getDayOfMonth == 31)
    assert(d1.plusMonths(2).getMonthValue == 3)
    assert(d1.minusMonths(1).getMonth == Month.DECEMBER)
    assert(d1.plusYears(1).getYear == 2015)
    assert(d1.minusYears(1).getYear == 2013)
    assert(d1.withDayOfMonth(3).getDayOfMonth == 3)
    assert(d1.withMonth(3).getMonthValue == 3)
    assert(d1.withYear(2013).getYear == 2013)
    assert(LocalDate.of(2014, 1, 3).isAfter(LocalDate.of(2014, 1, 1)))
    assert(LocalDate.of(2014, 1, 1).isBefore(LocalDate.of(2014, 1, 3)))
    assert(d1 == LocalDate.parse("2014-01-01"))
  }

  test("local time") {
    val t1 = LocalTime.of(3, 3)
    val t2 = LocalTime.of(3, 3, 0)
    val t3 = LocalTime.of(3, 3, 0, 0)
    assert(t1 == t2)
    assert(t1 == t3)
    assert(t2 == t3)
    assert(t1.plusHours(6).getHour == 9)
    assert(t1.minusHours(2).getHour == 1)
    assert(t1.plusMinutes(30).getMinute == 33)
    assert(t1.minusMinutes(2).getMinute == 1)
    assert(t1.withHour(9).getHour == 9)
    assert(t1.withMinute(33).getMinute == 33)
    assert(t1 == LocalTime.parse("03:03:00"))
  }

  test("time zone") {
    val cst = ZonedDateTime.now(ZoneId.of("US/Central"))
    val est = ZonedDateTime.now(ZoneId.of("US/Eastern"))
    assert(est.getHour - cst.getHour == 1)
  }

  test("period") {
    val p = Period.of(3, 3, 3)
    val d = LocalDate.of(2013, 3, 3).plus(p)
    assert(d == LocalDate.parse("2016-06-06"))
  }

  test("duration") {
    val d = Duration.of(33, ChronoUnit.SECONDS)
    val t = LocalTime.of(3, 3, 0).plus(d)
    assert(t == LocalTime.parse("03:03:33"))
    val from = LocalDateTime.now
    val to = LocalDateTime.now.plusHours(3).plusMinutes(6)
    assert(toDuration(from, to) == "Hours: 3, Minutes: 6, Seconds: 0, Millis: 2")
  }

  def toDuration(from: LocalDateTime, to: LocalDateTime): String = {
    var temp = LocalDateTime.from(from)
    val hours = temp.until(to, ChronoUnit.HOURS)
    temp = temp.plusHours(hours)
    val minutes = temp.until(to, ChronoUnit.MINUTES)
    temp = temp.plusMinutes(minutes)
    val seconds = temp.until(to, ChronoUnit.SECONDS)
    val millis = temp.until(to, ChronoUnit.MILLIS)
    s"Hours: $hours, Minutes: $minutes, Seconds: $seconds, Millis: $millis"
  }
}