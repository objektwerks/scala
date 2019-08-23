package collection

import java.util.function.BinaryOperator

import org.scalatest.{FunSuite, Matchers}

import scala.jdk.CollectionConverters._

class ConvertersTest extends FunSuite with Matchers {
  test("asJava") {
    val list = List(1, 2, 3).asJava
    list.size shouldEqual 3
    list.stream.count shouldEqual 3
    list.stream.reduce(new BinaryOperator[Int] {
      override def apply(t: Int, u: Int): Int = t + u
    }).get shouldEqual 6
  }

  test("asScala") {
    val list = List(1, 2, 3).asJava.asScala
    list.size shouldEqual 3
    list.sum shouldEqual 6
  }
}