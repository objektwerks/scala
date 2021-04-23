/*
  Given the following csv data, select a single price for every store, host, and UPC combination.
  The price selected should be based on the day of the week that the price was collected, where the
  days are ranked by priority.

  Weekday priority (high to low): Wednesday(1), Thursday(2), Friday(3), Saturday(4), Tuesday(5), Monday(6), Sunday(7)

  Csv data is: 1 row corresponds to 1 line, all fields do not contain ','
  Csv schema is: date(0), host(1), store_id(2), postal_code(3), upc(4), price(5)
*/

import scala.io.{Codec, Source}
import scala.util.Using

val utf8 = Codec.UTF8.name

val lines = Using( Source.fromInputStream(getClass.getResourceAsStream("/pricing.csv"), utf8) ) { 
  source => source.mkString
}.getOrElse( Array.empty[String] )

