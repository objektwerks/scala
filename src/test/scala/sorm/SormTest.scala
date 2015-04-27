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
  initMode = InitMode.Create)

class SormTest extends FunSuite {
  test("db") {
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
}