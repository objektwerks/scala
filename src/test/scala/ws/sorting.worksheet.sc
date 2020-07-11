val xs = List(2, 3, 1)
xs.sorted
xs.sortBy(i => i)
xs.sortWith(_ < _)
xs.sortWith(_ > _)

case class Person(name: String)
object Person {
  implicit def ordering: Ordering[Person] = Ordering.by(_.name)
}

val persons = List(Person("john"), Person("betty"), Person("george"))
persons.sorted
persons.sortBy(_.name)
persons.sortWith(_.name < _.name)
persons.sortWith(_.name > _.name)