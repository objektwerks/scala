package async

import java.net.URL
import java.nio.charset.StandardCharsets

import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.async.Async.{async, await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source

object AsyncRest {
  private implicit lazy val formats = DefaultFormats
  private val url = new URL("http://api.icndb.com/jokes/random/")
  private val utf8 = StandardCharsets.UTF_8.name()

  def asyncJoke: Future[String] = async {
    val jsonFuture = getJson
    val json = await(jsonFuture)
    await(parseJson(json))
  }

  private def getJson = Future {
    try {
      Source.fromURL(url, utf8).mkString
    } catch {
      case t: Throwable => t.getMessage
    }
  }

  private def parseJson(json: String) = Future {
    try {
      val ast = parse(json)
      (ast \ "value" \ "joke").extract[String]
    } catch {
      case t: Throwable => t.getMessage
    }
  }
}

/*
{ "type": "success",
  "value": { "id": 111,
             "joke": "Chuck Norris has a deep and abiding respect for human life... unless it gets in his way.",
             "categories": []
           }
}

JObject(List((type, JString(success)),
             (value, JObject(List((id, JInt(174)),
                                 (joke, JString(Chuck Norris cannot love, he can only not kill.)),
                                 (categories, JArray(List())))))))
 */
