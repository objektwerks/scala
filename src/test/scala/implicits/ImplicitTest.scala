package implicits

import org.scalatest.FunSuite

class ImplicitTest extends FunSuite {
  test("implicit conversion") {
    implicit def intToString(i: Int): String = i.toString
    val three: String = 3
    assert(three == "3")
  }

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

  test("package object") {
    val message = "test"
    assert(packMessage(message) == s"Message packed: $message")
  }

  test("implicitly") {
    val tasks: List[Task] = List(Task("c"), Task("b"), Task("a"))
    val sortedTasks: List[Task] = tasks.sorted(orderByTaskName)
    assert(sortedTasks.head.name == "a")
  }
}