package implicits

import org.scalatest.FunSuite

class ImplicitTest extends FunSuite {
  test("implicit conversion") {
    implicit def intToString(i: Int): String = i.toString
    val three: String = 3
    assert(three == "3")
  }

  test("implicit parameter") {
    implicit val item = "beers"
    def order(number: Int) (implicit item: String): String = {
      s"$number $item"
    }
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

  test("implicit sorting") {
    case class Runner(task: String)
    implicit def ord: Ordering[Runner] = Ordering.by(t => t.task)
    val runners: List[Runner] = List(Runner("c"), Runner("b"), Runner("a"))
    val sortedRunners: List[Runner] = runners.sorted
    assert(sortedRunners.head.task == "a")
  }

  test("implicitly") {
    case class Name(name: String)
    implicit val implicitName = Name("Fred Flintstone")
    assert(implicitly[Name] == implicitName)
  }

  test("package object") {
    val message = "test"
    assert(packMessage(message) == s"Message packed: $message")
  }
}