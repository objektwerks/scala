import scala.reflect.runtime.universe._

def show[T](value: T)(implicit tag: TypeTag[T]) = tag.toString.replace("package.object", s"${value.toString}")

trait Natural
class _0 extends Natural
class Next[N <: Natural] extends Natural

type _1 = Next[_0]
type _2 = Next[_1]
type _3 = Next[_2]

trait <[A <: Natural, B <: Natural]
object < {
  def apply[A <: Natural, B <: Natural](implicit ilt: <[A, B]) = ilt

  implicit def lt[B <: Natural]: <[_0, Next[B]] = new <[_0, Next[B]] {}
  implicit def ltx[A <: Natural, B <: Natural](implicit ilt: <[A, B]): <[Next[A], Next[B]] = {
    assert(ilt != null)
    new <[Next[A], Next[B]] {}
  }
}

trait <=[A <: Natural, B <: Natural]
object <= {
  def apply[A <: Natural, B <: Natural](implicit ilte: <=[A, B]) = ilte

  implicit def lte[B <: Natural]: <=[_0, Next[B]] = new <=[_0, Next[B]] {}
  implicit def ltex[A <: Natural, B <: Natural](implicit ilte: <=[A, B]): <=[Next[A], Next[B]] = {
    assert(ilte != null)
    new <=[Next[A], Next[B]] {}
  }
}

trait +[A <: Natural, B <: Natural, S <: Natural]
object + {
  def apply[A <: Natural, B <: Natural, S <: Natural](implicit plus: +[A, B, S]): +[A, B, S] = plus

  implicit val zero = new +[_0, _0, _0] {}

  implicit def AplusZeqA[A <: Natural](implicit lt: _0 < A): +[_0, A, A] = {
    assert(lt != null)
    new +[_0, A, A] {}
  }
  implicit def ZplusAeqA[A <: Natural](implicit lt: _0 < A): +[A, _0, A] = {
    assert(lt != null)
    new +[A, _0, A] {}
  }

  implicit def plusx[A <: Natural, B <: Natural, S <: Natural](implicit plus: +[A, B, S]): +[Next[A], Next[B], Next[Next[S]]] = {
    assert(plus != null)
    new +[Next[A], Next[B], Next[Next[S]]] {}
  }
}

// show
println( show( List(1, 2, 3) ) )

// less than
println( show( <[_0, _1] ) )
println( show( <[_0, _2] ) )
println( show( <[_0, _3] ) )
println( show( <[_1, _2] ) )
println( show( <[_1, _3] ) )
println( show( <[_2, _3] ) )

// less than equal
println( show( <=[_0, _1] ) )
println( show( <=[_0, _2] ) ) 
println( show( <=[_0, _3] ) )
println( show( <=[_1, _2] ) )
println( show( <=[_1, _3] ) )
println( show( <=[_2, _3] ) )

// plus
// val three: +[_1, _2, _3] = +.apply
// println( show( three ) )