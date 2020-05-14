import scala.util.Try

val divide: PartialFunction[Int, Int] = {
  case d: Int if d != 0 => d / 1
}
val error = Try { List(0, 1, 2) map divide }
val noError = List(0, 1, 2) collect divide
val noCats = List(42, "cat") collect { case i: Int => divide(i) }

val divideExt = new PartialFunction[Int, Int] {
  def apply(i: Int): Int = i / 1
  def isDefinedAt(i: Int): Boolean = i != 0
}
val apply = divideExt(2)
val apply2 = divideExt(0)
val isDefinedAt = divideExt.isDefinedAt(3)
val isDefinedAtZero = divideExt.isDefinedAt(0)

val isEven: PartialFunction[Int, String] = {
  case x if x % 2 == 0 => x + " is even"
}
val isOdd: PartialFunction[Int, String] = {
  case x if x % 2 == 1 => x + " is odd"
}
val even = 1 to 3 collect isEven
val odd = 1 to 3 collect isOdd
val collectEvenOrOdd = 1 to 3 collect (isEven orElse isOdd)
val mapEvenOrOdd = 1 to 3 map (isEven orElse isOdd)