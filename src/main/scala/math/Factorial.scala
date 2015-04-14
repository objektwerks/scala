package math

import scala.annotation.tailrec

object Factorial {
  def nonTailCallableFactorial(n: Long): Long = n match {
    case i if i <= 0 => 1
    case _ => n * nonTailCallableFactorial(n - 1)
  }

  @tailrec
  def tailCallableFactorial(n: Long, acc: Long = 1): Long = n match {
    case i if i <= 0 => acc
    case _ => tailCallableFactorial(n - 1, acc * n)
  }
}