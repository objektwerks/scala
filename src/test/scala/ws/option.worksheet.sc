def isPositive(i: Int): Option[Int] =
  if (i > 0) Some(i) else None

isPositive(1).get
isPositive(0).getOrElse(1)
isPositive(0) orElse Some(-1)
isPositive(0).isEmpty
isPositive(1).nonEmpty
isPositive(1).isDefined
isPositive(1) collect { case i: Int => i * 3 }
isPositive(1).contains(1)
isPositive(1).count(_ > 0)
isPositive(1).exists(_ > 0)
isPositive(1).filter(_ > 0)
isPositive(1).filterNot(_ > 0)
isPositive(1).forall(_ > 0)

def toInt(s: String): Option[Int] = s.toIntOption
val xs = List("1", "2", "3", "four")
xs.map(toInt)
xs.map(toInt).collect { case Some(i) => i }
xs.map(toInt).flatten
xs.flatMap(toInt)
xs.flatMap(toInt).sum

val optionOfListOfOptions = Option(List(Some(1), Some(2), Some(3)))
val sum = for {
  listOfOptions <- optionOfListOfOptions
} yield listOfOptions.flatten.sum
sum.getOrElse(-1)