package design

import java.time.{LocalDate, LocalDateTime}

import design.Meals.Meals

import scala.concurrent.duration._

case class Student(name: String)

case class Grade(student: Student, year: Int, from: LocalDate, to: Option[LocalDate])

case class Subject(name: String, description: String)

case class Assignment(subject: Subject, description: String)

case class Task(student: Student,
                assignment: Assignment,
                assigned: LocalDateTime,
                completed: Option[LocalDateTime],
                result: Option[String])

case class Chore(student: Student, chore: String, assigned: LocalDate, completed: Option[LocalDate])

case class Exercise(student: Student, exercise: String, completed: LocalDate, duration: Duration)

object Meals extends Enumeration {
  type Meals = Value
  val breakfast, lunch, dinner, snack = Value
}

case class Meal(student: Student, meal: Meals, description: String, consumed: LocalDateTime)