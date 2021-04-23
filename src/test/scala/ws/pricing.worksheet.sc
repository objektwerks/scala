/* 
  Given the following csv data, select a single price for every host, store, and UPC combination.
  The price selected should be based on the day of the week that the price was collected, where the
  days are ranked by priority.

  Weekday priority (high to low): Wednesday(1), Thursday(2), Friday(3), Saturday(4), Tuesday(5), Monday(6), Sunday(7)

  Csv data is: 1 row corresponds to 1 line, all fields do not contain ','
  Csv schema is: date(0), host(1), store_id(2), postal_code(3), upc(4), price(5)

  Output: ( Priority - Weekday ) -> ( Date -> Pricings )
  Type: mutable.Map[String, Map[String, List[Pricing]]]()
  Sorted: Sorted as: priority - weekday.
*/
import java.time._

import scala.collection.SortedMap
import scala.collection.mutable
import scala.io.{Codec, Source}
import scala.util.Using

case class Pricing(date: String, host: String, store: String, upc: String, price: String)

val weekdaysByPriority = Map[String, Int](
    DayOfWeek.WEDNESDAY.toString -> 1,
    DayOfWeek.THURSDAY.toString -> 2, 
    DayOfWeek.FRIDAY.toString -> 3, 
    DayOfWeek.SATURDAY.toString -> 4, 
    DayOfWeek.TUESDAY.toString -> 5, 
    DayOfWeek.MONDAY.toString -> 6, 
    DayOfWeek.SUNDAY.toString -> 7
  )

def buildKey(date: String): String = {
  val weekday = LocalDate.parse(date).getDayOfWeek().toString()
  val priority = weekdaysByPriority(weekday)
  s"$priority - $weekday"
}

def buildPricingMap(file: String): SortedMap[String, Map[String, List[Pricing]]] =
  Using( Source.fromInputStream(getClass.getResourceAsStream(file), Codec.UTF8.name) ) { source => 
    val pricings = mutable.ArrayBuffer[Pricing]()
    for (line <- source.getLines()) {
      val columns = line.split(",").map(_.trim)
      if ( columns.size == 6 ) {
        val date = columns(0)
        val host = columns(1)
        val store = columns(2)
        val upc = columns(4)
        val price = columns(5)
        val pricing = Pricing(date, host, store, upc, price)
        pricings += pricing
      }
    }
    val pricingsByDate = pricings.toList.groupBy(_.date)
    val pricingsByDateByWeekday = mutable.SortedMap[String, Map[String, List[Pricing]]]()
    for ( (key, value) <- pricingsByDate ) {
        pricingsByDateByWeekday += buildKey(key) -> Map(key -> value)
    }
    pricingsByDateByWeekday
  }.getOrElse( SortedMap.empty[String, Map[String, List[Pricing]]] )

// In worksheet, hover over buildPricingMap method to see output.
buildPricingMap("/pricing.csv")