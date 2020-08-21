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

trait +[A <: Natural, B <: Natural] { type Result <: Natural }
object + {
  type Plus[A <: Natural, B <: Natural, S <: Natural] = +[A, B] { type Result = S }

  implicit val zero = new +[_0, _0] { type Result = _0 }
  implicit val one = new +[_0, _1] { type Result = _1 }
  implicit val two = new +[_0, _2] { type Result = _2 }
  implicit val three = new +[_0, _3] { type Result = _3 }

  def apply[A <: Natural, B <: Natural](implicit plus: +[A, B]): Plus[A, B, plus.Result] = plus
 
  implicit def AplusZeqA[A <: Natural](implicit lt: _0 < A): +[_0, A] = {
    assert(lt != null)
    new +[_0, A] { type Result = A }
  }

  implicit def ZplusAeqA[A <: Natural](implicit lt: _0 < A): +[A, _0] = {
    assert(lt != null)
    new +[A, _0] { type Result = A }
  }

  implicit def plusx[A <: Natural, B <: Natural, S <: Natural](implicit plus: Plus[A, B, S]): Plus[Next[A], Next[B], Next[Next[S]]] = {
    assert(plus != null)
    new +[Next[A], Next[B]] { type Result = Next[Next[S]] }
  }
}

// show
println( "List(1, 2, 3) > " + show( List(1, 2, 3) ) )

// less than
println( "0 < 1 > > " + show( <[_0, _1] ) )
println( "0 < 2 > " + show( <[_0, _2] ) )
println( "0 < 3 > " + show( <[_0, _3] ) )
println( "1 < 2 > " + show( <[_1, _2] ) )
println( "1 < 3 > " + show( <[_1, _3] ) )
println( "2 < 3 > " + show( <[_2, _3] ) )

// less than equals
println( "0 <= 1 > " + show( <=[_0, _1] ) )
println( "0 <= 2 > " + show( <=[_0, _2] ) ) 
println( "0 <= 3 > " + show( <=[_0, _3] ) )
println( "1 <= 2 > " + show( <=[_1, _2] ) )
println( "1 <= 3 > " + show( <=[_1, _3] ) )
println( "2 <= 3 > " + show( <=[_2, _3] ) )

// plus ... illegal start of simple expression scalameta compiler error ... compiles and runs in Types object!
/*
println( "1 + 2 > " + show( +[_1, _2] ) )
println( "0 + 0 > " + show( +[_0, _0] ) )
println( "1 + 1 > " + show( +[_1, _1] ) )
println( "1 + 2 > " + show( +[_1, _2] ) )
println( "1 + 3 > " + show( +[_1, _3] ) )
println( "2 + 2 > " + show( +[_2, _2] ) )
println( "2 + 3 > " + show( +[_2, _3] ) )
println( "3 + 3 > " + show( +[_3, _3] ) )
*/