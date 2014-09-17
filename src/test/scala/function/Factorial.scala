package function

import scala.annotation.tailrec

object Factorial {
  def nonTailCallableFactorial(n: Int): Int = n match {
    case i if i <= 0 => 1
    case _ => n * nonTailCallableFactorial(n - 1)
  }

  @tailrec
  def tailCallableFactorial(n: Int, acc: Int = 1): Int = n match {
    case i if i <= 0 => acc
    case _ => tailCallableFactorial(n - 1, acc * n)
  }
}