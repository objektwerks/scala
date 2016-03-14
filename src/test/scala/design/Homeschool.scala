package design

import java.time.{LocalDate, LocalDateTime}

import design.Chores.Chores
import design.Exercises.Exercises
import design.Meals.Meals
import design.Targets.Targets

import scala.concurrent.duration._

case class Student(name: String)

case class Grade(student: Student, year: Int, from: LocalDate = LocalDate.now, to: Option[LocalDate])

case class Subject(name: String, description: String)

case class Assignment(subject: Subject, description: String)

case class Task(student: Student,
                assignment: Assignment,
                assigned: LocalDateTime = LocalDateTime.now,
                completed: Option[LocalDateTime],
                result: Option[String])

object Chores extends Enumeration {
  type Chores = Value
  val clean, dust, vacuum = Value
}

object Targets extends Enumeration {
  type Targets = Value
  val bathroom, bedroom, livingroom, den, garage, kitchen, microwave, oven, refrigerator = Value
}

case class Chore(student: Student, chore: Chores, target: Targets, assigned: LocalDate = LocalDate.now, completed: Boolean = false)

object Exercises extends Enumeration {
  type Exercises = Value
  val walking, swimming = Value
}

case class Exercise(student: Student, exercise: Exercises, duration: Duration, description: String)

object Meals extends Enumeration {
  type Meals = Value
  val breakfast, lunch, dinner, snack = Value
}

case class Meal(student: Student, meal: Meals, description: String)