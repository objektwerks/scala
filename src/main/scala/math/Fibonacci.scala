package math

import scala.annotation.tailrec

object Fibonacci {
  def tailrec(n: Long): BigInt = {
    @tailrec
    def loop(n: Long, a: Long, b: Long): BigInt = n match {
      case 0 => a
      case _ => loop(n - 1, b, a + b)
    }
    loop(n, 0, 1)
  }

  def naiveRecursive(n : Long) : BigInt = n match {
    case 0 | 1 => n
    case _ => naiveRecursive(n - 1) + naiveRecursive(n - 2)
  }

  def tailRecursive(n: Long, a: Long, b: Long): BigInt = n match {
    case 0 => a
    case _ => tailRecursive(n - 1, b, a + b)
  }

  def sequence(a: Int = 0, b: Int = 1): List[Int] = {
    def build(a: Int = 0, b: Int = 1): Stream[Int] = Stream.cons(a, build(b, a + b))
    build().takeWhile(_>= 0).toList
  }
}