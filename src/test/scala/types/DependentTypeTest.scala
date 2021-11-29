package types

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

trait Box {
  type Value
  val value: Value
}

object Box {
  def deriveValue(box: Box): box.Value = box.value
}

class DependentTypeTest extends AnyFunSuite with Matchers {
  test("dependent") {
    import Box._

    def valueOf[T](t: T) = new Box {
      type Value = T
      val value: T = t
    }

    val intValue    = valueOf(1)
    val stringValue = valueOf("one")

    deriveValue(intValue) shouldBe 1
    deriveValue(stringValue) shouldBe "one"
  }
}