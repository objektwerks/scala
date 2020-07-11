import scala.util.{Failure, Success, Try}

def toIntWithTry(s: String): Try[Int] = Try(s.toInt)

def toIntWithEither(s: String): Either[Throwable, Int] = Try(s.toInt).toEither

def toIntWithOption(s: String): Option[Int] = Try(s.toInt).toOption

val xs = List("1", "2", "3", "four")

val t = xs.map(toIntWithTry).map(t => t.getOrElse(0)).sum

val e = xs.map(toIntWithEither).map(e => e.getOrElse(0)).sum

val o = xs.flatMap(toIntWithOption).sum

val tm = toIntWithTry("a") match {
  case Success(i) => i
  case Failure(f) => f
}

val em = toIntWithEither("a") match {
  case Right(i) => i
  case Left(l) => l
}

val om = toIntWithOption("a") match {
  case Some(i) => i
  case None => None
}

val tmo = toIntWithTry("a").toOption.getOrElse(0)
val tme = toIntWithTry("a").toEither.getOrElse(0)
val tmoEqualtme = tmo == tme

val two = toIntWithOption("a").getOrElse(0)
val twe = toIntWithEither("a").getOrElse(0)
val twoEqualtwe = two == twe

val t0 = Try("a".toInt).getOrElse(0)