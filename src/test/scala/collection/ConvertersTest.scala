package collection

import org.scalatest.FunSuite

import scala.jdk.CollectionConverters._

class ConvertersTest extends FunSuite {
  test("converters") {
    val list = List(1, 2, 3).asJava
    assert(list.size == 3)
    assert(list.asScala.sum == 6)
  }
}