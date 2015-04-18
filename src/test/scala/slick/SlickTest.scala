package slick

import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

class SlickTest extends FunSuite with BeforeAndAfterAll {
  private implicit val ec = ExecutionContext.global

  override protected def beforeAll(): Unit = {
    super.beforeAll()
    Store.createSchema()
  }

  override protected def afterAll(): Unit = {
    super.afterAll()
    Store.dropSchema()
    Store.close()
  }

  test("insert person") {
    val fred = Person(name = "fred", age = 21)
    val barney = Person(name = "barney", age = 19)
    val futureFred = Store.insert(fred)
    val futureBarney = Store.insert(barney)
    val fredId = Await.ready(futureFred, Duration.Inf).value.get.get
    val barneyId = Await.ready(futureBarney, Duration.Inf).value.get.get
    println(s"Fred inserted id: $fredId")
    println(s"Barney inserted id: $barneyId")
  }

  test("insert task") {
    val futureFred = Store.findPerson("fred")
    val futureBarney = Store.findPerson("barney")
    val fred = Await.ready(futureFred, Duration.Inf).value.get.get
    val barney = Await.ready(futureBarney, Duration.Inf).value.get.get
    val futureFredTask = Store.insert(Task(personId = fred.id.get, task = "Mow yard."))
    val futureBarneyTask = Store.insert(Task(personId = barney.id.get, task = "Clean pool."))
    val fredTaskId = Await.ready(futureFredTask, Duration.Inf).value.get.get
    val barneyTaskId = Await.ready(futureBarneyTask, Duration.Inf).value.get.get
    println(s"Fred inserted task id: $fredTaskId")
    println(s"Barney inserted task id: $barneyTaskId")
    list()
  }

  private def list(): Unit = {
    val futurePersons = Store.listPersons
    val persons = Await.ready(futurePersons, Duration.Inf).value.get.get
    for (p <- persons) {
      println(p)
      val futureTasks = Store.listTasks(p)
      val tasks = Await.ready(futureTasks, Duration.Inf).value.get.get
      for (t <- tasks) {
        println(s"\t$t")
      }
    }
  }
}