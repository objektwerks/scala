package parser

import org.scalatest.FunSuite

import scala.util.parsing.combinator.RegexParsers

class ExprParser extends RegexParsers {
  val number = "[1-9]+".r
  def expr: Parser[Int] = (number ^^ { _.toInt }) ~ opt(operator ~ expr ) ^^ {
    case a ~ None => a
    case a ~ Some("*" ~ b) => a * b
    case a ~ Some("/" ~ b) => a / b
    case a ~ Some("+" ~ b) => a + b
    case a ~ Some("-" ~ b) => a - b
  }
  def operator: Parser[Any] = "+" | "-" | "*" | "/"
}

class ParserTest extends FunSuite {
  test("find all in") {
    val regex = "am".r
    val source = "I am, I am, said he."
    assert((regex findAllIn source size) == 2)
  }

  test("replace all in") {
    val regex = "cats".r
    val source = "I love cats, and cats love me."
    val replacement = "tigers"
    assert((source length) == 30)
    assert((regex replaceAllIn(source, replacement) length) == 34)
  }

  test("pattern match") {
    val date = """(\d\d\d\d)-(\d\d)-(\d\d)""".r
    "2015-09-09" match {
      case date(year, month, day) => assert(year.toInt == 2015 && month.toInt == 9 && day.toInt == 9)
    }
  }

  test("parse all") {
    val parser = new ExprParser()
    assert(parser.parseAll(parser.expr, "3+3").get == 6)
    assert(parser.parseAll(parser.expr, "3*3").get == 9)
    assert(parser.parseAll(parser.expr, "9-6").get == 3)
    assert(parser.parseAll(parser.expr, "9/3").get == 3)
  }
}