package fx

import dispatch._
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods._

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}

class AsyncRest {
  private implicit val ec = ExecutionContext.global
  private implicit lazy val formats = DefaultFormats

  def joke: String = {
    Http.configure(_.setConnectTimeout(10000))
    val service = url("http://api.icndb.com/jokes/random/")
    val request = Http(service.GET)
    val response = Await.result(request, 10 seconds)
    response.getStatusCode match {
      case 200 => parseJson(response.getResponseBody)
      case _ => s"${response.getStatusCode} : ${response.getStatusText}"
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
                                 (categories, JArray(List)))))))
 */