import scala.annotation.tailrec
import scala.util.Random
import scala.util.chaining._

val isEven = (x: Int) => x % 2 == 0
List(1, 2, 3, 4, 5, 6).filter(isEven)

def square(x: Int): Int = x * x
List(1, 2, 3, 4).map(square)
val eta = square _
eta(3)

def toInt(s: String): Option[Int] = s.toIntOption
List("1", "2", "3", "four").flatMap(toInt).sum

def callByValue(nextRandomInt: Int): (Int, Int) = (nextRandomInt, nextRandomInt)
callByValue(Random.nextInt())

def callByName(nextRandomInt: => Int): (Int, Int) = (nextRandomInt, nextRandomInt)
callByName(Random.nextInt())

def defaultArg(x: Int, y: Int = 3): Int = x * y
defaultArg(3)

def varArgs(varargs: Int*): Int = varargs.sum
varArgs(1, 2, 3)
varArgs(List(1, 2, 3):_*)

val isOfAge = 21
def closure(age: Int): Boolean = age >= isOfAge
closure(22)

def product(x: Int, y: Int): Int = x * y
val partiallyAppliedProduct = product(3, _: Int)
partiallyAppliedProduct(3)

def multiplier(x: Int)(y: Int): Int = x * y
val partiallyAppliedMultiplier = multiplier(3)(_)
partiallyAppliedMultiplier(3)

def curry(x: Int): Int => Int = (y: Int) => x * y
curry(3)(3)

val sum: (Int, Int) => Int = (x, y) => x + y
val curriedSum = sum.curried
curriedSum(3)(3)

def add(x: Int, y: Int): Int = x + y
val curriedAdd = (add _).curried
curriedAdd(3)(3)

val (three, six) = (3, 6)
val sumTupled = sum.tupled
val sumTupledApply = sumTupled.apply((three, six))
val tuple = (3, 6, 9)
tuple.copy(13, 15, 18)

val partialFunction = new PartialFunction[Int, Int] {
  def apply(i: Int): Int = i * 2
  def isDefinedAt(i: Int): Boolean = i != 0
}
partialFunction(2)
partialFunction.isDefinedAt(42)
partialFunction.isDefinedAt(0)
List(0, 1, 2) map partialFunction
List(0, 1, 2) collect partialFunction
List(42, "cat") collect { case i: Int => partialFunction(i) }

def higherOrderFunction(f: (Int, Int) => Int,
                        g: (Int, Int) => Int)
                       (x: Int, y: Int): (Int, Int) = (f(x, y), g(x, y))
higherOrderFunction(sum, product)(3, 3)

def filter[A](p: A => Boolean)(xs: Seq[A]): Seq[A] = xs filter p
filter(isEven)(List(1, 2, 3, 4))

def map[A, B](f: A => B)(xs: Seq[A]): Seq[B] = xs map f
map(square)(List(1, 2, 3))

@tailrec
final def factorial(n: Int, acc: Int = 1): Int = n match {
  case i if i < 1 => acc
  case _ => factorial(n - 1, acc * n)
}
factorial(9)

def fibonacci(n: Long): Long = {
  @tailrec
  def loop(n: Long, a: Long, b: Long): Long = n match {
    case 0 => a
    case _ => loop(n - 1, b, a + b)
  }
  loop(n, 0, 1)
}
fibonacci(39)

def isPrime(n: Int): Boolean = {
  @tailrec
  def loop(current: Int): Boolean = {
    if (current > Math.sqrt( Math.abs(n.toDouble)) ) true
    else n % current != 0 && loop(current + 1)
  }
  if (n == -1 || n == 0 || n == 1) false else loop(2)
}
isPrime(11)

val incr = (n: Int) => n + 1
val decr = (n: Int) => n - 1
val incrComposeDecr = incr compose decr
val incrAndThenDecr = incr andThen decr
val xs = (1 to 3).toList
xs map incr
xs map decr
xs map incr map decr
xs map incrComposeDecr
xs map incrAndThenDecr
xs map incrComposeDecr map incrAndThenDecr

2.pipe(square) == 4

def timer[A](codeblock: => A): (A, Double) = {
  val startTime = System.nanoTime
  val result = codeblock
  val stopTime = System.nanoTime
  val delta = stopTime - startTime
  (result, delta/1000000d)
}
val (result, time) = timer { factorial(19) }

val caseExpression: Any => String = {
  case i: Int => s"$i is an Int type."
  case d: Double => s"$d is a Double type."
  case _ => "_ is an Any type."
}
caseExpression(1)
caseExpression(1.0)
caseExpression("1")

def diffAsPercentage(previous: Double, current: Double): Int = {
  val dividend = current - previous
  val divisor = ( current + previous ) / 2
  val delta = Math.abs( dividend / divisor ) * 100
  delta.round.toInt
}

diffAsPercentage(70.0, 75.0)
diffAsPercentage(75.0, 70.0)
diffAsPercentage(75.0, 80.0)