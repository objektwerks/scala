package types

import org.scalatest.FunSuite

// Product | Has-A-And Pattern
trait C
trait B
trait A { // A has-a B and C
def b: B
  def c: C
}
case class D(b: B, c: C)  // D has-a B and C

// Product | Has-A-Or Pattern
sealed trait P
final case class Q(q: Any) extends P
final case class R(r: Any) extends P
trait O {  // O has-a Q or R
def p: P
}
final case class H(b: B) extends P  // O is-a H or I, and H has-a B and I has-a C
final case class I(c: C) extends P

// Sum | Is-A-Or Pattern
sealed trait Z  // Z is-a X or Y
final case class X(x: Any) extends Z
final case class Y(y: Any) extends Z

// Sum | Is-A-And Pattern
trait L
trait M
trait N extends L with M  // N is-a L and M

class PatternsTest extends FunSuite {
  test("patterns") {

  }
}