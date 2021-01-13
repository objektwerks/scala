package types

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

trait Speaking { 
  def speaking: String 
}
trait Hello extends Speaking { 
  override def speaking = "hello" 
}
trait Goodbye extends Speaking { 
  override def speaking = "goodbye" 
}
class Speaker {
  self: Speaking =>
  def speak: String = speaking
}

class SelfTypeTest extends AnyFunSuite with Matchers {
    test("self type") {
    val helloSpeaker = new Speaker() with Hello
    helloSpeaker.speak shouldEqual "hello"

    val goodbyeSpeaker = new Speaker() with Goodbye
    goodbyeSpeaker.speak shouldEqual "goodbye"
  }
}