package future

import org.scalatest.{FunSuite, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util._

class SyncAsyncTest extends FunSuite with Matchers {
  test("sync / async") {
    val futureA = Future("a")
    def futureB(a: String): Future[String] = Future {
      /*throw new Exception("b failed");*/ a + "b"
    }
    def futureC(a: String): Future[String] = Future {
      /*throw new Exception("c failed");*/ a + "c"
    }
    def futureD(bTry: Try[String], cTry: Try[String]) = Future {
      val bLog = if(bTry.isFailure) " b failed" else ""
      val cLog = if(cTry.isFailure) " c failed" else ""

      val result = for{
        b <- bTry
        c <- cTry
      } yield b + c +"d"

      result.getOrElse(bLog+cLog)
    }

    def futureBandC(a: String) = {
      futureB(a)
        .map(a => Success(a))
        .recover { case f => Failure(f) }
        .zip(
          futureC(a)
            .map(b => Success(b))
            .recover { case f => Failure(f) }
        )
    }

    val futureResult =
      (for {
        a <- futureA
        (b, c) <- futureBandC(a) //run futureB and futureC in parralel and keep both erros if they fail
        d <- futureD(b, c)
      } yield d).recover {
        case error => error.getMessage
      }


    val result = futureResult.recover { case failure => Future(failure.getMessage) }
    Await.result(result, Duration.Inf) shouldBe "abacd"
  }
}