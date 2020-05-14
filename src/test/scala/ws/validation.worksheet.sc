class Validation(val field: String, val rule: String, predicate: => Boolean) {
  def isValid: Boolean = predicate
  def isInvalid: Boolean = !predicate
}

object Validation {
  def apply(field: String, rule: String, predicate: => Boolean): Validation = {
    new Validation(field, rule, predicate)
  }
}

case class Violation(field: String, rule: String)

case class Person(first: String, last: String)

object Validators {
  private def run(validations: Validation*): Option[Seq[Violation]] = {
    val violations = validations
      .filter(validation => validation.isInvalid)
      .map(validation => Violation(validation.field, validation.rule))
    if (violations.nonEmpty) Some(violations) else None
  }

  implicit class PersonOps(val person: Person) {
    def validate: Option[Seq[Violation]] = run(
      Validation("first", "must be nonempty", person.first.nonEmpty),
      Validation("last", "must be nonempty", person.last.nonEmpty)
    )
  }
}

import Validators._

val person = Person("Fred", "")
val violations = person.validate
val isInvalid = violations.nonEmpty