import scala.util.{Failure, Success, Try}

def retry[T](n: Int)(fn: => T): T =
    Try { fn } match {
      case Success(result) => result
      case _ if n >= 1 => retry(n - 1)(fn)
      case Failure(error) => throw error
    }

