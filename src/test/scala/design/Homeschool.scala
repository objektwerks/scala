package design

import java.time.{LocalDate, LocalDateTime}

import design.Chores.Chores
import design.Exercises.Exercises
import design.Meals.Meals
import design.ChoreTargets.ChoreTargets

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
  val clean, dust, vacuum, wash = Value
}

object ChoreTargets extends Enumeration {
  type ChoreTargets = Value
  val bathroom, bedroom, livingroom, den, garage, kitchen, microwave, oven, refrigerator = Value
}

case class Chore(student: Student, chore: Chores, target: ChoreTargets, assigned: LocalDate = LocalDate.now, completed: Boolean = false)

object Exercises extends Enumeration {
  type Exercises = Value
  val aerobics, cycling, running, swimming, walking, weights = Value
}

case class Exercise(student: Student, exercise: Exercises, duration: Duration, description: String)

object Meals extends Enumeration {
  type Meals = Value
  val breakfast, lunch, dinner, snack = Value
}

case class Meal(student: Student, meal: Meals, description: String)