package collection

import org.scalatest.{FunSuite, Matchers}

import scala.jdk.CollectionConverters._

class ConvertersTest extends FunSuite with Matchers {
  test("asJava") {
    val asJavaList = List(1, 2, 3).asJava
    asJavaList.size shouldEqual 3
    asJavaList.stream.count shouldEqual 3
    asJavaList.stream.reduce((t: Int, u: Int) => t + u).get shouldEqual 6
  }

  test("asScala") {
    val arrayList = new java.util.ArrayList[Int]()
    arrayList.add(1)
    arrayList.add(2)
    arrayList.add(3)
    val asScalaBuffer = arrayList.asScala
    asScalaBuffer.size shouldEqual 3
    asScalaBuffer.sum shouldEqual 6
  }
}