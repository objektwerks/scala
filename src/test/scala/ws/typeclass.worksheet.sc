trait Sum[T] {
  def sum(list: List[T]): T
}

implicit object IntSum extends Sum[Int] {
  def sum(list: List[Int]): Int = list.sum
}

implicit object StringSum extends Sum[String] {
  def sum(list: List[String]): String = list.mkString("")
}

def sumList[T](list: List[T])(implicit implementor: Sum[T]): T =
  implementor.sum(list)

sumList(List(1, 2, 3)) 
sumList(List("Scala ", "is ", "awesome!"))