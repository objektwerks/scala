import scala.io.Source

class IO[A] private (codeblock: => A) {
  def run = codeblock
  def flatMap[B](f: A => IO[B]): IO[B] = IO(f(run).run)
  def map[B](f: A => B): IO[B] = flatMap(a => IO(f(a)))
}

object IO {
  def apply[A](a: => A): IO[A] = new IO(a)
}

val quote = "A penny saved, is a penny earned!"
val countWords: IO[Unit] = for {
  words <- IO { Source.fromString(quote).mkString.split("\\P{L}+") }
  _     <- IO { println(s"$quote : ${words.length}") }
} yield ()
countWords.run