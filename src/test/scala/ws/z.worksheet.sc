import scala.annotation.tailrec

/*
  1. Fibonacci Sequence.
*/

def fibonacci(n: Long): Long = {
  @tailrec
  def loop(n: Long, a: Long, b: Long): Long = n match {
    case 0 => a
    case _ => loop(n - 1, b, a + b)
  }
  loop(n, 0, 1)
}

def fibonacci(ns: Array[String]): Array[Long] = {
  def toLong(s: String): Option[Long] = s.toLongOption
  ns.flatMap( n => toLong(n) ).map( n => fibonacci(n) )
}

val ns = Array("3", "6", "9", "four")
fibonacci(ns)

/*
  2. Reverse Strings.
*/

@tailrec
final def reverse[A](list: List[A], acc: List[A] = List.empty[A]): List[A] = list match {
  case Nil => acc
  case head :: tail => reverse(tail, head :: acc)
}

reverse( Array(1, 2, 3).toList )
reverse( Array("Hello", "World").toList )

/*
  3. Prime Numbers.
*/

def isPrime(n: Int): Boolean = {
  @tailrec
  def loop(current: Int): Boolean = {
    if (current > Math.sqrt(Math.abs(n.toDouble))) true
    else n % current != 0 && loop(current + 1)
  }
  if (n == -1 || n == 0 || n == 1) false else loop(2)
}

def isPrime(ns: Array[String]): Array[(Int, Boolean)] = {
  def toInt(s: String): Option[Int] = s.toIntOption
  ns.flatMap( n => toInt(n) ).map( n => (n, isPrime(n) ) )
}

isPrime( Array("3", "5", "11", "15") )