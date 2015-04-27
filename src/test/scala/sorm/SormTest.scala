package sorm

import com.typesafe.config.ConfigFactory
import org.scalatest.FunSuite

case class Student(name: String, tasks: Set[Task])

case class Task(task: String)

object Db extends Instance(
  entities = Set(Entity[Student](), Entity[Task]()),
  url = ConfigFactory.load().getString("sorm.url"),
  user = "",
  password = "",
  initMode = InitMode.DropCreate)

class SormTest extends FunSuite {
  test("save") {
    val fredTask = Db.save(Task("reading"))
    println(fredTask)
    assert(fredTask.id > 0)

    val fred = Db.save(Student("fred", Set(fredTask)))
    println(fred)
    assert(fred.id > 0)

    val barneyTask = Db.save(Task("writing"))
    println(barneyTask)
    assert(barneyTask.id > 0)

    val barney = Db.save(Student("barney", Set(barneyTask)))
    println(barney)
    assert(barney.id > 0)
  }

  test("fetch") {
    val students = Db.query[Student].fetch()
    println(students)
    assert(students.length > 1)

    val fred = Db.query[Student].whereEqual("name", "fred").fetchOne()
    println(fred)
    assert(fred.get.name == "fred")

    val barney = Db.query[Student].whereEqual("name", "barney").fetchOne()
    println(barney)
    assert(barney.get.name == "barney")
  }
}