
package ammonite
package $file.src.test.scala.ws
import _root_.ammonite.interp.api.InterpBridge.{
  value => interp
}
import _root_.ammonite.interp.api.InterpBridge.value.{
  exit
}
import _root_.ammonite.interp.api.IvyConstructor.{
  ArtifactIdExt,
  GroupIdExt
}
import _root_.ammonite.runtime.tools.{
  browse,
  grep,
  time,
  tail
}
import _root_.ammonite.repl.tools.{
  desugar,
  source
}
import _root_.ammonite.main.Router.{
  doc,
  main
}
import _root_.ammonite.repl.tools.Util.{
  pathScoptRead
}


object `state.transformer`{
/*<script>*/import scala.language.higherKinds

class IO[A] private (codeblock: => A) {
  def run = codeblock
  def flatMap[B](f: A => IO[B]): IO[B] = IO(f(run).run)
  def map[B](f: A => B): IO[B] = flatMap(a => IO(f(a)))
}

object IO {
  def apply[A](a: => A): IO[A] = new IO(a)
}

trait Monad[M[_]] {
  def lift[A](a: => A): M[A]
  def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
  def map[A, B](ma: M[A])(f: A => B): M[B] = flatMap(ma)(a => lift[B](f(a)))
}

implicit val IOMonad = new Monad[IO] {
  def lift[A](a: => A): IO[A] = IO(a)
  def flatMap[A, B](ma: IO[A])(f: A => IO[B]): IO[B] = ma.flatMap(f)
}

case class StateT[M[_], S, A](run: S => M[(S, A)]) {
  def flatMap[B](f: A => StateT[M, S, B])(implicit M: Monad[M]): StateT[M, S, B] = StateT { currentState =>
    M.flatMap(run(currentState)) {
      case (newState, a) => f(a).run(newState)
    }
  }
  def map[B](f: A => B)(implicit M: Monad[M]): StateT[M, S, B] = flatMap(a => StateT.point(f(a)))
}

object StateT {
  def point[M[_], S, A](value: A)(implicit M: Monad[M]): StateT[M, S, A] = StateT(run = state => M.lift((state, value)))
}

case class Value(value: Int)

def lift[A](io: IO[A]): StateT[IO, Value, A] = StateT { value => io.map(a => (value, a)) }

def sum(value: Int): StateT[IO, Value, Int] = StateT[IO, Value, Int] { currentState =>
  val newValue = value + currentState.value
  val newState = currentState.copy(value = newValue)
  IO(newState, newValue)
}

val sumOp: StateT[IO, Value, Int] = for {
  _ <- lift(IO { println(s"[ The sum ...") } )
  _ <- sum(2)
  _ <- sum(3)
  value <- sum(3)
  _ <- lift(IO { println(s"total is $value ]") } )
} yield value

val result: IO[(Value, Int)] = sumOp.run(Value(1))
val (value, sum) = result.run
/*</script>*/ /*<generated>*/
def $main() = { scala.Iterator[String]() }
  override def toString = "state$u002Etransformer"
  /*</generated>*/
}
