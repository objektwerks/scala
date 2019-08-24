package sys

import org.scalatest.{FunSuite, Matchers}

import scala.sys.SystemProperties
import scala.sys.process.Process

class SysTest extends FunSuite with Matchers {
  test("system properties") {
    val properties = new SystemProperties
    properties.contains("java.runtime.name") shouldBe true

    properties += ("objekt" -> "werks")
    properties.contains("objekt") shouldBe true

    properties -= "objekt"
    properties.getOrElse("objekt", "empty") shouldEqual "empty"
  }

  test("process") {
    val file = Process("ls").lazyLines.find(file => file == "build.sbt")
    file.getOrElse("empty") shouldEqual "build.sbt"

    val line = Process("find build.sbt").lazyLines.headOption
    line.getOrElse("empty") shouldEqual "build.sbt"

    val lines = Process("cat .gitignore").lazyLines
    lines.length shouldEqual 6
  }
}