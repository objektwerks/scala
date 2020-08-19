import scala.reflect.runtime.universe._

def show[T](value: T)(implicit tag: TypeTag[T]) = tag.toString.replace("content.TypeLevelProgramming.", s"${value.toString}")
println(show(List(1, 2, 3)))