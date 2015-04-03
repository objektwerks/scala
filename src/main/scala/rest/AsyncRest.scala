package rest

import dispatch._
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods._

import scala.async.Async.{async, await}
import scala.concurrent.ExecutionContext.Implicits.{global => ec}
import scala.concurrent.Future

object AsyncRest {
  private implicit lazy val formats = DefaultFormats
  private val jokeUrl = "http://api.icndb.com/jokes/random/"

  def asyncJoke: Future[String] = async {
    await(futureJoke)
  }

  def futureJoke: Future[String] = {
    val future = Http(url(jokeUrl) OK as.String)
    future map {
      json => parseJson(json)
    }
  }

  private def parseJson(json: String): String = {
    val ast = parse(json)
    (ast \ "value" \ "joke").extract[String]
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