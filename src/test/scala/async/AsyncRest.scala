package async

import scala.async.Async.{async, await}
import scala.concurrent.Future

object AsyncRest extends RestDelegate {
  def asyncJoke: Future[String] = async {
    val jsonFuture = getJson
    val json = await(jsonFuture)
    await(parseJson(json))
  }
}