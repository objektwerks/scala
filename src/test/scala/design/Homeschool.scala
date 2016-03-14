package design

import java.time.{LocalDate, LocalDateTime}

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

case class Category(category: String)

case class Type(category: Category, value: String)

case class Chore(student: Student, chore: Type, assigned: LocalDate, completed: Option[LocalDate])

case class Exercise(student: Student, exercise: Type, completed: LocalDate, duration: Duration)

case class Meal(student: Student, meal: Type, description: String, consumed: LocalDateTime)