package z

import org.scalatest.FunSuite

import scalaz._
import Scalaz._

class DisjunctionTest extends FunSuite {
  test("disjunction") {
    "success".right.merge assert_=== "success"
    "failure".left.merge assert_=== "failure"

    \/.right("success").merge assert_=== "success"
    \/.left("failure").merge assert_=== "failure"

    \/-("success").merge assert_=== "success"
    -\/("failure").merge assert_=== "failure"

    false /\ true assert_=== false
    false \/ true assert_=== true
  }
}