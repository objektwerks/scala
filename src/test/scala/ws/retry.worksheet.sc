import scala.io.{Codec, Source}
import scala.util.{Failure, Success, Try, Using}

def retry[T](n: Int)(fn: => T): T =
    Try { fn } match {
      case Success(result) =>
        println(s"success: $result")
        result
      case _ if n >= 1 =>
        println(s"remaining retries: ${n - 1}")
        retry(n - 1)(fn)
      case Failure(error) =>
        println(s"error: ${error.getMessage()}")
        throw error
    }

val utf8 = Codec.UTF8.name
val url = "http://api.icndb.com/jokes/random/"
val badUrl = "http://api.icndb.org/"
val retries = 3

def joke(): String =
  Using( Source.fromURL(badUrl, utf8) ) { 
    source => source.mkString
  }.get

val result = retry[String](retries)(joke())