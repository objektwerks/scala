/* 
  Given the following csv data, select a single price for every host, store, and upc combination. The
  price selected should be based on the day of the week that the price was collected, where the days
  are ranked by priority.
  
  Priority: Wednesday(1), Thursday(2), Friday(3), Saturday(4), Tuesday(5), Monday(6), Sunday(7)
  Schema: date(0), host(1), store_id(2), postal_code(3), upc(4), price(5)
  Result: SortedMap[PricingKey, Set[Pricing]]
  Sorted: priority - weekday - date

  Issue: date, host and store are repeated in Set[Pricing]
*/
import java.time._

import scala.collection.SortedMap
import scala.collection.mutable
import scala.io.{Codec, Source}
import scala.util.{Failure, Success, Try, Using}

case class Pricing(date: String, host: String, store: String, upc: String, price: String)

case class PricingKey(priority: Int, weekday: String, date: String)
object PricingKey {
  implicit def ordering = Ordering.by[PricingKey, Int](_.priority)
}

val weekdaysByPriority = Map[String, Int](
  DayOfWeek.WEDNESDAY.toString -> 1,
  DayOfWeek.THURSDAY.toString -> 2, 
  DayOfWeek.FRIDAY.toString -> 3, 
  DayOfWeek.SATURDAY.toString -> 4, 
  DayOfWeek.TUESDAY.toString -> 5, 
  DayOfWeek.MONDAY.toString -> 6, 
  DayOfWeek.SUNDAY.toString -> 7
)

def buildPricingKey(date: String): PricingKey = {
  val weekday = LocalDate.parse(date).getDayOfWeek().toString()
  val priority = weekdaysByPriority(weekday)
  PricingKey(priority, weekday, date)
}

def buildPricingMap(classpathFile: String): Try[SortedMap[PricingKey, Set[Pricing]]] =
  Using( Source.fromInputStream(getClass.getResourceAsStream(classpathFile), Codec.UTF8.name) ) { source => 
    val pricings = mutable.Set[Pricing]() // eliminate duplicates
    for (line <- source.getLines()) {
      val columns = line.split(",").map(_.trim)
      if ( columns.size == 6 ) { // ignore malformed lines
        val date = columns(0)
        val host = columns(1)
        val store = columns(2)
        val upc = columns(4)
        val price = columns(5)
        val pricing = Pricing(date, host, store, upc, price)
        pricings += pricing
      } else println(s"*** invalid line: $line")
    }
    val pricingsByDate = pricings.groupBy(_.date)
    val pricingsByKey = mutable.SortedMap[PricingKey, Set[Pricing]]()
    for ( (key, value) <- pricingsByDate ) {
      pricingsByKey += buildPricingKey(key) -> value.toSet // requires immutable Set
    }
    pricingsByKey
  }

// In worksheet, hover over this block to see invalid and valid pricing data.
buildPricingMap(classpathFile = "/pricing.csv") match {
  case Success(pricingMap) =>
    for ( (key, value) <- pricingMap ) {
      println(s"*** key: $key")
      println(s"*** value(${value.size}): $value")
    }
  case Failure(failure) => println(s"*** failure: $failure")
}