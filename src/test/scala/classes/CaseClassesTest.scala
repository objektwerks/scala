package classes

import org.scalatest.FunSuite

class CaseClassesTest extends FunSuite {
  trait Animal {
    def sound(): String
  }

  case class Tiger(speach: String) extends Animal {
    def sound(): String = speach
  }

  case class Shark(speach: String) extends Animal {
    def sound(): String = speach
  }

  case class Bear(speach: String) extends Animal {
    def sound(): String = speach
  }

  object ZooKeeper {
    def openCages(): Set[Animal] = Set(new Tiger("prrrr"), new Shark("woosh"), new Bear("grrrr"))
  }

  test("animals") {
    val animals = ZooKeeper.openCages()
    for(animal <- animals) {
      assert(animal.sound().length > 0)
      animal match {
        case Tiger(s) => assert(s == "prrrr")
        case Shark(s) => assert(s == "woosh")
        case Bear(s) => assert(s == "grrrr")
      }
    }
  }

  test("equality") {
    val t1 = Tiger("prrrr")
    val t2 = Tiger("prrrr")
    val t3 = Tiger("meow")
    assert(t1 == t2)
    assert(t1 != t3)
    assert(t1.hashCode == t2.hashCode)
    assert(t1.hashCode != t3.hashCode)
    assert(Tiger.unapply(t1).get == "prrrr")
    assert(t1 == Tiger.apply(t1.sound()))
  }

  test("case classes with builder") {
    case class Addresses(from: String, to: String, cc: String = "")
    case class Message(subject: String, text: String)
    case class Email(addresses: Addresses, message: Message)
    class EmailBuilder() {
      private var email: Option[Email] = None

      def build(addresses: Addresses, message: Message): this.type = {
        email = Some(Email(addresses, message))
        this
      }

      def send(): Option[Email] = {
        email
      }
    }
    val builder = new EmailBuilder()
    val email = builder.build(Addresses("me", "you", "them"), Message("us", "Meet as the pub for beer!")).send()
    assert(email != None)
  }

  test("generic function") {
    def selectMiddleItem[A](a: Array[A]): A = a(a.length / 2)
    assert(selectMiddleItem(Array("a", "b", "c")) == "b")
  }
}