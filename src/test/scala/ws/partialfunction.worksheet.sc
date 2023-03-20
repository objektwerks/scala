import scala.util.Try

val multiplyByTwo: PartialFunction[Int, Int] = {
  case i: Int if i != 0 => i * 2
}

Try { List(0, 1, 2) map multiplyByTwo } // map fails on match error!
List(0, 1, 2) collect multiplyByTwo
List(7, "cat") collect { case i: Int => multiplyByTwo(i) }

val divideByOne = new PartialFunction[Int, Int] {
  def apply(i: Int): Int = i / 1
  def isDefinedAt(i: Int): Boolean = i != 0
}

divideByOne(2)
divideByOne(0)
divideByOne.isDefinedAt(3)
divideByOne.isDefinedAt(0)

val isEven: PartialFunction[Int, String] = {
  case i if i % 2 == 0 => s"$i even"
}

val isOdd: PartialFunction[Int, String] = {
  case i if i % 2 == 1 => s"$i odd"
}

1 to 3 collect isEven
1 to 3 collect isOdd
1 to 3 collect (isEven orElse isOdd)
1 to 3 map (isOdd orElse isEven)