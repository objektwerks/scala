package types

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

final case class Glucose(datetime: Long, level: Int)
final case class Med(datetime: Long, medtype: Int, dosage: Int)

class TypeReflectTest extends AnyFunSuite with Matchers {
  test("class tag") {
    import scala.reflect._

    def isTypeof[T: ClassTag](list: List[T]): ClassTag[T] = classTag[T] match {
      case g if g === classTag[Glucose] => println(s"*** glucose class tag: ${list.toString}"); g
      case m if m === classTag[Med] => println(s"*** med class tag: ${list.toString}"); m
      case _ => fail("class tag test failed!")
    }

    isTypeof(List.empty[Glucose]) === classTag[Glucose] shouldBe true
    isTypeof(List.empty[Med]) === classTag[Med] shouldBe true
  }

  test("type tag") {
    import scala.reflect.runtime.universe._

    def isTypeof[T: TypeTag](list: List[T]): TypeTag[T] = typeTag[T] match {
      case g if g === typeTag[Glucose] => println(s"*** glucose type tag: ${list.toString}"); g
      case m if m === typeTag[Med] => println(s"*** med type tag: ${list.toString}"); m
      case _ => fail("type tag test failed!")
    }

    isTypeof(List.empty[Glucose]) === typeTag[Glucose] shouldBe true
    isTypeof(List.empty[Med]) === typeTag[Med] shouldBe true
  }
}