package parser

import org.scalatest.{FunSuite, Matchers}

import scala.util.parsing.combinator.RegexParsers

class ExprParser extends RegexParsers {
  val number = "[1-9]+".r
  def expr: Parser[Int] = (number ^^ { _.toInt }) ~ opt(operator ~ expr ) ^^ {
    case a ~ Some("+" ~ b) => a + b
    case a ~ Some("-" ~ b) => a - b
    case a ~ Some("*" ~ b) => a * b
    case a ~ Some("/" ~ b) => a / b
    case a ~ Some(_) => a
    case a ~ None => a
  }
  def operator: Parser[Any] = "+" | "-" | "*" | "/"
}

class ParserTest extends FunSuite with Matchers {
  test("find all in") {
    val regex = "am".r
    val source = "I am, I am, said he."
    (regex findAllIn source size) shouldEqual 2
  }

  test("replace all in") {
    val regex = "cats".r
    val source = "I love cats, and cats love me."
    val replacement = "tigers"
    (source length) shouldEqual 30
    (regex replaceAllIn(source, replacement) length) shouldEqual 34
  }

  test("pattern match") {
    val date = """(\d\d\d\d)-(\d\d)-(\d\d)""".r
    "2015-09-09" match {
      case date(year, month, day) =>
        year.toInt shouldEqual 2015
        month.toInt shouldEqual 9
        day.toInt shouldEqual 9
    }
  }

  test("parse all") {
    val parser = new ExprParser()
    parser.parseAll(parser.expr, "3+3").get shouldEqual 6
    parser.parseAll(parser.expr, "3*3").get shouldEqual 9
    parser.parseAll(parser.expr, "9-6").get shouldEqual 3
    parser.parseAll(parser.expr, "9/3").get shouldEqual 3
  }
}