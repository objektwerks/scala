package types

object Types {
  import scala.reflect.runtime.universe._

  def show[T](value: T)(implicit tag: TypeTag[T]) = tag.toString.replace("types.Types", s"${value.toString}")
  println(show(List(1, 2, 3)))

  trait Natural
  class _0 extends Natural
  class Next[N <: Natural] extends Natural

  type _1 = Next[_0]
  type _2 = Next[_1]
  type _3 = Next[_2]

  trait <[A <: Natural, B <: Natural]
  object < {
    implicit def lt[B <: Natural]: <[_0, Next[B]] = new <[_0, Next[B]] {}
    implicit def ltx[A <: Natural, B <: Natural](implicit lt: <[A, B]): <[Next[A], Next[B]] = {
      println(lt)
      new <[Next[A], Next[B]] {}
    }
    def apply[A <: Natural, B <: Natural](implicit lt: <[A, B]) = lt
  }

  trait <=[A <: Natural, B <: Natural]
  object <= {
    implicit def lte[B <: Natural]: <=[_0, Next[B]] = new <=[_0, Next[B]] {}
    implicit def ltex[A <: Natural, B <: Natural](implicit lte: <=[A, B]): <=[Next[A], Next[B]] = {
      println(lte)
      new <=[Next[A], Next[B]] {}
    }
    def apply[A <: Natural, B <: Natural](implicit lt: <[A, B]) = lt
  }

  def main(args: Array[String]): Unit = {
    println(show( <[_0, _1] ))
    println(show( <[_0, _2] ))
    println(show( <[_0, _3] ))
    println(show( <[_1, _2]) )
    println(show( <[_2, _3]) )

    println(show( <=[_0, _1] ))
    println(show( <=[_0, _2] ))
    println(show( <=[_0, _3] ))
    println(show( <=[_1, _2]) )
    println(show( <=[_2, _3]) )
  }
}