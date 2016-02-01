package sys

import org.scalatest.FunSuite

import scala.sys.SystemProperties

class SysTest extends FunSuite {
  test("system properties") {
    val properties = new SystemProperties
    assert(properties.contains("java.runtime.name"))
    properties += ("objekt" -> "werks")
    assert(properties.contains("objekt"))
    properties -= "objekt"
    assert(properties.getOrElse("objekt", "empty") == "empty")
  }
}