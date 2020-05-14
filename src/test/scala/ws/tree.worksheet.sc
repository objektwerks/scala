sealed trait Tree[+A]
case object Empty extends Tree[Nothing]
final case class Node[A](value: A,
                         left: Tree[A],
                         right: Tree[A]) extends Tree[A]

val tree = Node(value = 42,
                left = Node(value = 0,
                            left = Empty,
                            right = Empty),
                right = Empty)