package design

import java.time.{LocalDateTime, LocalDate}

case class Student(name: String, grades: Set[Grade])

case class Grade(grade: Int, school: String, from: LocalDate, to: Option[LocalDate], courses: Set[Course])

case class Course(name: String, tasks: Set[Task])

case class Task(task: String, assigned: LocalDateTime, completed: Option[LocalDateTime])