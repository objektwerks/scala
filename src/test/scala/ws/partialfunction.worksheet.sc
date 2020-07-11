import scala.util.Try

val divide: PartialFunction[Int, Int] = {
  case d: Int if d != 0 => d / 1
}

Try { List(0, 1, 2) map divide }
List(0, 1, 2) collect divide
List(42, "cat") collect { case i: Int => divide(i) }

val divideExt = new PartialFunction[Int, Int] {
  def apply(i: Int): Int = i / 1
  def isDefinedAt(i: Int): Boolean = i != 0
}

divideExt(2)
divideExt(0)
divideExt.isDefinedAt(3)
divideExt.isDefinedAt(0)

val isEven: PartialFunction[Int, String] = {
  case x if x % 2 == 0 => s"$x is even"
}

val isOdd: PartialFunction[Int, String] = {
  case x if x % 2 == 1 => s"$x is odd"
}

1 to 3 collect isEven
1 to 3 collect isOdd
1 to 3 collect (isEven orElse isOdd)
1 to 3 map (isEven orElse isOdd)