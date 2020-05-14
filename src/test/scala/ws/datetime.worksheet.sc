import java.time.{LocalDate, LocalTime}
import java.time.format.DateTimeFormatter

val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

def localDateToString(localDate: LocalDate): String = {
  localDate.format(dateFormatter)
}

def localDateToInt(localDate: LocalDate): Int = {
  localDateToString(localDate).replace("-", "").toInt
}

def yyyyMMddToInt(yyyy: Int, mm: Int, dd: Int): Int = {
  localDateToInt(LocalDate.of(yyyy, mm, dd))
}

def localDateAsStringToInt(localDate: String): Int = {
  localDate.replace("-", "").toInt
}

def localDateAsIntToString(localDate: Int): String = {
  val localDateAsString = localDate.toString
  val yyyy = localDateAsString.substring(0, 4)
  val mm = localDateAsString.substring(4, 6)
  val dd = localDateAsString.substring(6, 8)
  LocalDate.of(yyyy.toInt, mm.toInt, dd.toInt).format(dateFormatter)
}

def localTimeToString(localTime: LocalTime): String = {
  localTime.format(timeFormatter)
}

def localTimeToInt(localTime: LocalTime): Int = {
  localTimeToString(localTime).replace(":", "").toInt
}

def hhMMToInt(hh: Int, mm: Int): Int = {
  localTimeToInt(LocalTime.of(hh, mm))
}

def localTimeAsStringToInt(localTime: String): Int = {
  localTime.replace(":", "").toInt
}

def localTimeAsIntToString(localTime: Int): String = {
  val localTimeAsString = localTime.toString
  var hh = ""
  var mm = ""
  if (localTimeAsString.length == 3) {
    hh = localTimeAsString.substring(0, 1)
    mm = localTimeAsString.substring(1, 3)
  } else {
    hh = localTimeAsString.substring(0, 2)
    mm = localTimeAsString.substring(2, 4)
  }
  LocalTime.of(hh.toInt, mm.toInt).format(timeFormatter)
}

val localDateAsString = localDateToString(LocalDate.now)
val localDateAsInt = localDateToInt(LocalDate.now)
val localDateAsInt2 = yyyyMMddToInt(1991, 3, 13)
val localDateAsInt3 = localDateAsStringToInt(localDateAsString)
val localDateAsString2 = localDateAsIntToString(localDateAsInt3)

val localTimeAsString = localTimeToString(LocalTime.now)
val localTimeAsInt = localTimeToInt(LocalTime.now)
val localTimeAsInt2 = hhMMToInt(3, 33)
val localTimeAsInt3 = localTimeAsStringToInt(localTimeAsString)
val localTimeAsString2 = localTimeAsIntToString(localTimeAsInt3)
