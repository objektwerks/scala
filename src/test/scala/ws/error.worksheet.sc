import scala.util.{Failure, Success, Try}

def toIntWithTry(s: String): Try[Int] = Try(s.toInt)

def toIntWithEither(s: String): Either[Throwable, Int] = Try(s.toInt).toEither

def toIntWithOption(s: String): Option[Int] = Try(s.toInt).toOption

val xs = List("1", "2", "3", "four")

xs.map(toIntWithTry).map(t => t.getOrElse(0)).sum
xs.map(toIntWithEither).map(e => e.getOrElse(0)).sum
xs.flatMap(toIntWithOption).sum

toIntWithTry("a") match {
  case Success(i) => i
  case Failure(f) => f
}

toIntWithEither("a") match {
  case Right(i) => i
  case Left(l) => l
}

toIntWithOption("a") match {
  case Some(i) => i
  case None => None
}

toIntWithTry("a").toOption.getOrElse(0)
toIntWithTry("a").toEither.getOrElse(0)

toIntWithOption("a").getOrElse(0)
toIntWithEither("a").getOrElse(0)

Try("a".toInt).getOrElse(0)

val leftEither: Either[String, String] = Left("error")
for {
  value <- leftEither
} yield value.toUpperCase()

val rightEither: Either[String, String] = Right("success")
for {
  value <- rightEither
} yield value.toUpperCase()