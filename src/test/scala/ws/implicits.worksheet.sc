// Parameters
implicit val item = "beers"
def order(number: Int)(implicit item: String): String = {
  s"$number $item"
}
val beers = order(2)

// Conversions
implicit def intToString(i: Int): String = i.toString
val three: String = 3

// Extensions
object StringExtensions {
  implicit class Methods(val s: String) {
    def toJson = s"{$s}"
    def toXml = s"<$s>"
  }
}
import StringExtensions._
val json = "json".toJson
val xml = "xml".toXml

// Ordering
case class Value(number: Int)
object Value {
  implicit class ValueCombiner(val value: Value) {
    def +(other: Value): Value = Value(value.number + other.number)
  }
  implicit def ordering: Ordering[Value] = Ordering.by(_.number)
}
import Value._
val values = List(3, 2, 1).map(n => Value(n))
val combinedValue = values.foldLeft(Value(0))(_ + _)
val sorted = values.sorted