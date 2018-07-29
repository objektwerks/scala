package recursion

import org.scalatest.FunSuite

import scala.annotation.tailrec

case class Heading(weight: Int, text: String) {
  def indent: String = {
    val newLine = "\n"
    val tabs = "\t" * weight
    s"$newLine$tabs$text"
  }
}

case class Node(heading: Heading, children: List[Node]) {
  def toOutline: String = {
    @tailrec
    def loop(node: Node, outline: String): String = node match {
      case Node(h, c) if c.isEmpty => outline + h.indent
      case Node(h, t) => loop(t.head, outline + h.indent)
    }
    loop(this, "")
  }
}

class NodeRecursionTest extends FunSuite {
  test("node to outline") {
    val node = Node(Heading(0, "1. All about Birds"),
      List(Node(Heading(1, "1. Kinds of Birds"),
        List(Node(Heading(2, "1. The Finch"),
          List(Node(Heading(2, "2. The Swan"),
            List(Node(Heading(1, "2. Habitats"),
              List(Node(Heading(2, "1. Wetlands"),
                List.empty[Node])))))))))))
    val outline = node.toOutline
    assert(outline.nonEmpty)
    println(outline)
  }
}