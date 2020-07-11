import scala.annotation.tailrec
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

@tailrec
final def factorial(n: Int, acc: Int = 1): Int = n match {
  case i if i < 1 => acc
  case _ => factorial(n - 1, acc * n)
}

// Parallel futures.
val f1 = Future { factorial(3) }
val f2 = Future { factorial(6) }
val f3 = Future { factorial(9) }

// Await parallel futures in worksheet.
Await.result(f1,1 second)
Await.result(f2,1 second)
Await.result(f3,1 second)

// Sequentially collect parallel future results.
val result = for {
  r1 <- f1
  r2 <- f2
  r3 <- f3
} yield r1 + r2 + r3

// Match on success | failure result and print.
result.onComplete {
  case Success(r) => println(s"result success: $r")
  case Failure(e) => println(s"result error: $e")
}