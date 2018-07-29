package recursion

import org.scalatest.FunSuite

case class Heading(indention: Int, weight: Int, text: String) {
  def indent: String = {
    val newLine = "\n"
    val tabs = "\t" * indention
    s"$newLine$tabs$weight. $text"
  }
}

case class Node(heading: Heading, children: List[Node])

object Node {
  def printNode(node: Node, acc: String): String = node match {
    case Node(heading, children) => if (children.nonEmpty)
      printNode(children.head, acc + heading.indent)
    else acc + heading.indent
  }
}

class HtmlRecursionTest extends FunSuite {
  val doc = Node(Heading(0, 1, "All about Birds"),
    List(Node(Heading(1, 1, "Kinds of Birds"),
      List(Node(Heading(2, 1, "The Finch"),
        List(Node(Heading(2, 2, "The Swan"),
          List(Node(Heading(1, 2, "Habitats"),
            List(Node(Heading(2, 1, "Wetlands"),
              List.empty[Node])))))))))))

  test("html recursion") {
    import Node._
    val result = printNode(doc, "")
    assert(result.nonEmpty)
    println(result)
  }
}