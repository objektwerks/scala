val xs = List(2, 3, 1)
val sorted = xs.sorted
val sortBy = xs.sortBy(i => i)
val sortWithAsc = xs.sortWith(_ < _)
val sortWithDesc = xs.sortWith(_ > _)

case class Person(name: String)
object Person {
  implicit def ordering: Ordering[Person] = Ordering.by(_.name)
}

val persons = List(Person("john"), Person("betty"), Person("george"))
val sort = persons.sorted
val sortby = persons.sortBy(_.name)
val asc = persons.sortWith(_.name < _.name)
val desc = persons.sortWith(_.name > _.name)
