trait Combiner[T] {
  def combine(list: List[T]): T
}

implicit object IntCombiner extends Combiner[Int] {
  def combine(list: List[Int]): Int = list.sum
}

implicit object StringCombiner extends Combiner[String] {
  def combine(list: List[String]): String = list.mkString("")
}

def combineList[T](list: List[T])(implicit combiner: Combiner[T]): T = combiner.combine(list)

combineList(List(1, 2, 3)) 
combineList(List("Scala ", "is ", "awesome!"))