package theory

import org.scalatest.FunSuite

class MonadTest extends FunSuite {
  test("monad") {
    val list: List[String] = ListMonad.unit("a")
    val mappedListToUpperCase: List[String] = list map(a => a.toUpperCase)
    val flatMappedListToLowerCase: List[Char] = list flatMap(a => a.toLowerCase)
    println(mappedListToUpperCase)
    println(flatMappedListToLowerCase)
    assert(list.length + mappedListToUpperCase.length + flatMappedListToLowerCase.length == 3)
    val listOfList: List[List[String]] = ListMonad.unit("a" :: "b" :: "c" :: Nil)
    val flatMappedListOfList = listOfList flatMap(as => as.map(a => a.toUpperCase))
    println(listOfList)
    println(flatMappedListOfList)
    assert(listOfList.length == 1)
    assert(flatMappedListOfList.length == 3)
  }
}