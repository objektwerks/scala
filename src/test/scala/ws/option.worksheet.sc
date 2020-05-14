import scala.util.Try

def isPositive(i: Int): Option[Int] =
  if (i > 0) Some(i) else None

val get = isPositive(1).get
val getOrElse = isPositive(0).getOrElse(1)
val orElse = isPositive(0) orElse Some(-1)
val isEmpty = isPositive(0).isEmpty
val nonEmpty = isPositive(1).nonEmpty
val isDefined = isPositive(1).isDefined
val collect = isPositive(1) collect { case i: Int => i * 3 }
val contains = isPositive(1).contains(1)
val count = isPositive(1).count(_ > 0)
val exixts = isPositive(1).exists(_ > 0)
val filter = isPositive(1).filter(_ > 0)
val filterNot = isPositive(1).filterNot(_ > 0)
val forall = isPositive(1).forall(_ > 0)

def toInt(s: String): Option[Int] = Try(s.toInt).toOption
val xs = List("1", "2", "3", "four")
val map = xs.map(toInt)
val flatMap = xs.flatMap(toInt)
val sum = xs.flatMap(toInt).sum

val optionOfListOfOptions = Option(List(Some(1), Some(2), Some(3)))
val sumOfOptionOfListOfOptions = for {
  listOfOptions <- optionOfListOfOptions
} yield listOfOptions.flatten.sum
val optionSum = sumOfOptionOfListOfOptions.getOrElse(-1)
