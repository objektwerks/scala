/* 

Given the following csv data, select a single price for every host, store, and UPC combination.
The price selected should be based on the day of the week that the price was collected, where the
days are ranked by priority.

Weekday priority (high to low): Wednesday(1), Thursday(2), Friday(3), Saturday(4), Tuesday(5), Monday(6), Sunday(7)

Csv data is: 1 row corresponds to 1 line, all fields do not contain ','
Csv schema is: date(0), host(1), store_id(2), postal_code(3), upc(4), price(5)

*/

import scala.collection.mutable
import scala.io.{Codec, Source}
import scala.util.Using

case class DateKey(date: String)
case class Pricing(host: String, store: String, upc: String, price: String)

val utf8 = Codec.UTF8.name
val delimitter = ","

val lines = Using( Source.fromInputStream(getClass.getResourceAsStream("/pricing.csv"), utf8) ) { 
  source => source.getLines()
}.getOrElse( List.empty[String] )

val datePricingMap = mutable.Map[DateKey, Pricing]()
for (line <- lines) {
  val columns = line.split(delimitter).map(_.trim)
  val date = columns(0)
  val host = columns(1)
  val store = columns(2)
  val upc = columns(4)
  val price = columns(5)

  val dateKey = DateKey(date)
  val pricing = Pricing(host, store, upc, price)
  datePricingMap += (dateKey -> pricing)
}