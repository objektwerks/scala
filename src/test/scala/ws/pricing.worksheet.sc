/*
  Given the following csv data, select a single price for every store, host,
  and UPC combination. The price selected should be based on the day of the
  week that the price was collected, where the days are ranked by priority.

  Day of week priority (high to low): Wednesday, Thursday, Friday, Saturday, Tuesday, Monday, Sunday

  Assume csv data is very simple: 1 row corresponds to 1 line, all fields do not contain ','
  Csv schema: date,host,store_id,postal_code,upc,price
*/

// TODO!