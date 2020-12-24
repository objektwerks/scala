import scala.annotation.tailrec

/*
  1. Fibonacci Sequence.
  Input: Array("3", "6", "9", "four")
  Output: Array((3L, 2L), (6L, 8L), (9L, 34L))
*/

def fibonacci(n: Long): Long = {
  @tailrec
  def loop(n: Long, a: Long, b: Long): Long = n match {
    case 0 => a
    case _ => loop(n - 1, b, a + b)
  }
  loop(n, 0, 1)
}

def fibonacci(ns: Array[String]): Array[(Long, Long)] = {
  def toLong(s: String): Option[Long] = s.toLongOption
  ns.flatMap( n => toLong(n) ).map( n => (n, fibonacci(n)) )
}

val ns = Array("3", "6", "9", "four")
fibonacci(ns)

/*
  2. Reverse Strings.
  Input: Array("Hello", "World"), Array(1, 2, 3)
  Output: Array("World", "Hello"), Array(3, 2, 1)
*/

@tailrec
final def reverse[A](list: List[A], acc: List[A] = List.empty[A]): List[A] = list match {
  case Nil => acc
  case head :: tail => reverse(tail, head :: acc)
}

reverse( Array("Hello", "World").toList )
reverse( Array(1, 2, 3).toList )

/*
  3. Prime Numbers.
  Input: Array("3", "5", "11", "15")
  Output: Array((3, true), (5, true), (11, true), (15, false))
*/

def isPrime(n: Int): Boolean = {
  @tailrec
  def loop(current: Int): Boolean = {
    if (current > Math.sqrt(Math.abs(n.toDouble))) true
    else n % current != 0 && loop(current + 1)
  }
  if (n == -1 || n == 0 || n == 1) false else loop(2)
}

def isPrime(ns: Array[String]): Map[Int, Boolean] = {
  def toInt(s: String): Option[Int] = s.toIntOption
  ns.flatMap( n => toInt(n) ).map( n => (n, isPrime(n) ) ).toMap
}

isPrime( Array("3", "5", "11", "15") )