package async

import scala.async.Async.{async, await}
import scala.concurrent.Future

object AkkaRest extends RestDelegate {
  def asyncJoke: Future[String] = async {
    val jsonFuture = getJson
    val json = await(jsonFuture)
    await(parseJson(json))
  }
}