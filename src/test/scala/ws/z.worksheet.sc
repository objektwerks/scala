/* Z ( VSCode-Metals ) Worksheet for Interviews */

import scala.annotation.tailrec

@tailrec
final def sum(numbers: List[Int], acc: Int = 0): Int = numbers match {
  case Nil => acc
  case head :: tail => sum(tail, acc + head)
}

sum(List(1, 2, 3))