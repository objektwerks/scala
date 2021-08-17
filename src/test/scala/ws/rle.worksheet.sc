// RLE: aaaabbcccaeeeee => a4b2c3ae5


val string = "aaaabbcccaeeeee"
val chars = string.toCharArray
val grouped = chars
  .groupBy( char => char )
  .view
  .mapValues(_.length)

