package z

import org.scalatest.FunSuite

import scalaz._

case class Person(name: String, address: Address)

case class Address(street: String, city: String, state: String, zip: Int)

class PersonLens {
  val nameLens = Lens.lensu[Person, String] ((a, value) => a.copy(name = value),  _.name)
  val addressLens = Lens.lensu[Person, Address] ((a, value) => a.copy(address = value), _.address)
  val streetLens = Lens.lensu[Address, String] ((a, value) => a.copy(street = value), _.street)
  val cityLens = Lens.lensu[Address, String] ((a, value) => a.copy(city = value), _.city)
  val stateLens = Lens.lensu[Address, String] ((a, value) => a.copy(state = value), _.state)
  val zipLens = Lens.lensu[Address, Int] ((a, value) => a.copy(zip = value), _.zip)
}

class LensTest extends FunSuite {
  val person = new Person("Jack Sparrow", new Address("33 Sailor Way", "Prirate Cove", "FL", 33399))
  val personLens = new PersonLens

  test("person lens") {
    val name = personLens.nameLens.get(person)
    assert(name == person.name)

    val address = personLens.addressLens.get(person)
    assert(address == person.address)

    val street = personLens.streetLens.get(person.address)
    assert(street == person.address.street)

    val city = personLens.cityLens.get(person.address)
    assert(city == person.address.city)

    val state = personLens.stateLens.get(person.address)
    assert(state == person.address.state)

    val zip = personLens.zipLens.get(person.address)
    assert(zip == person.address.zip)
  }

  test("composite lens") {
    val personZipLens = personLens.addressLens andThen personLens.zipLens
    assert(personZipLens.get(person) == 33399)

    val addressZipLens = personLens.zipLens compose personLens.addressLens
    assert(addressZipLens.get(person) == 33399)
  }
}