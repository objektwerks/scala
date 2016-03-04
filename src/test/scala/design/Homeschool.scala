package design

import java.time.{LocalDateTime, LocalDate}

case class Student(name: String)

case class Grade(student: Student, year: Int, from: LocalDate = LocalDate.now, to: Option[LocalDate])

case class Subject(name: String, description: String)

case class Assignment(subject: Subject, description: String)

case class Task(student: Student, assignment: Assignment, assigned: LocalDateTime = LocalDateTime.now, completed: Option[LocalDateTime])