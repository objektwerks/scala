/* Z ( VSCode-Metals ) Worksheet for Interviews */

import scala.annotation.tailrec

def fibonacci(n: Long): Long = {
  @tailrec
  def loop(n: Long, a: Long, b: Long): Long = n match {
    case 0 => a
    case _ => loop(n - 1, b, a + b)
  }
  loop(n, 0, 1)
}
fibonacci(9)