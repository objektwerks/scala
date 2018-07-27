package recursion

import org.scalatest.FunSuite

case class Heading(weight: Int, text: String) {
  def indent: String = {
    val newLine = "\n"
    val tabs = "\t" * weight
    s"$newLine$tabs$weight. $text"
  }
}
case class Node(heading: Heading, children: List[Node])
object Node {
  def printNode(node: Node): String = {
    def printNodes(nodes: List[Node], acc: String): String = nodes match {
      case Nil => acc
      case head :: tail =>
        printNodes(tail, acc + head.heading.indent)
    }
    printNodes(node.children, node.heading.indent)
  }
}

class HtmlRecursionTest extends FunSuite {
  val doc = Node(Heading(1, "All about Birds"),
    List(Node(Heading(2, "Kinds of Birds"),
      List(Node(Heading(3, "The Finch"),
        List(Node(Heading(3, "The Swan"),
          List(Node(Heading(2, "Habitats"),
            List(Node(Heading(3, "Wetlands"), List.empty[Node])))))))))))

  test("html recursion") {
    import Node._
    println(printNode(doc))
  }
}