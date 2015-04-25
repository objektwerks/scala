package sorm

import org.scalatest.FunSuite

case class Student(name: String, tasks: Set[Task])

case class Task(task: String)

object Db extends Instance(
  entities = Set(Entity[Student](), Entity[Task]()),
  url = "jdbc:h2:mem:sorm",
  user = "",
  password = "",
  initMode = InitMode.Create)

class SormTest extends FunSuite {
  test("db") {
    val fredTask = Db.save(Task("reading"))
    println(fredTask)
    val fred = Db.save(Student("fred", Set(fredTask)))
    println(fred)
    val barneyTask = Db.save(Task("writing"))
    println(barneyTask)
    val barney = Db.save(Student("barney", Set(barneyTask)))
    println(barney)
  }
}