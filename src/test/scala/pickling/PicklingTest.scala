package pickling

import org.scalatest.FunSuite

import scala.pickling._
import scala.pickling.json._

case class Dill(brand: String)

class PicklingTest extends FunSuite {
  test("list pickling") {
    val list: List[Int] = List(1, 2, 3)
    val pickledList: JSONPickle = list.pickle
    println(pickledList)
    val unpickledList: List[Int] = pickledList.unpickle[List[Int]]
    println(unpickledList)
    assert(list == unpickledList)
  }

  test("case class pickling") {
    val dillAsJson: JSONPickle = Dill("Vlasic").pickle
    println(dillAsJson)
    val dill: Dill = dillAsJson.unpickle[Dill]
    println(dill)
  }
}