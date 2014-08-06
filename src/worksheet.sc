val list = List((1, 10), (1, 20), (1, 30))
val key: Int = list.head._1
val values: List[Int] = list.map(_._2).toList

val map: Map[Int, List[Int]] = Map(key -> values)
