package math

import scala.annotation.tailrec

object Factorial {
  def nonTailCallable(n: Long): Long = n match {
    case i if i <= 0 => 1
    case _ => n * nonTailCallable(n - 1)
  }

  @tailrec
  def tailCallable(n: Long, acc: Long = 1): Long = n match {
    case i if i <= 0 => acc
    case _ => tailCallable(n - 1, acc * n)
  }
}