package implicits

import org.scalatest.FunSuite

class ImplicitTest extends FunSuite {
  test("implicit parameter") {
    def order(number: Int) (implicit item: String): String = {
      s"$number $item"
    }
    implicit val item = "beers"
    assert(order(2) == "2 beers")

  }

  test("implicit class") {
    object Strings {
      implicit class EnhancedStrings(val s: String) {
        def toJson = s"{$s}"
        def toXml = s"<$s>"
      }
    }
    import Strings._
    assert("json".toJson == "{json}")
    assert("xml".toXml == "<xml>")
  }
}