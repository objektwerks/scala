package types

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

trait Box {
  type Value
  val value: Value
}

object Box {
  def unbox(box: Box): box.Value = box.value

  def box[T](t: T) = new Box {
    type Value = T
    val value: T = t
  }
}

class DependentTypeTest extends AnyFunSuite with Matchers {
  test("dependent") {
    import Box._

    val intValue = box(1)
    val stringValue = box("one")

    unbox(intValue) shouldBe 1
    unbox(stringValue) shouldBe "one"
  }
}