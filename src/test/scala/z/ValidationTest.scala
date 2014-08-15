package z

import org.scalatest.FunSuite

import scalaz.Scalaz._
import scalaz._

case class Profile(name: String, email: String)

object Profile {
  def validateName(name: String): Validation[String, String] = {
    if (name.isEmpty) "Please, enter your name.".failure else name.success
  }

  def validateEmail(email: String): Validation[String, String] = {
    if (email.isEmpty) "Please, enter your email.".failure else email.success
  }

  def validateProfile(profile: Profile) : ValidationNel[String, Profile] = {
    (validateName(profile.name).toValidationNel |@| validateEmail(profile.email).toValidationNel) {
      Profile(_, _)
    }
  }
}

class ValidationTest extends FunSuite {
  test("invalid profile") {
    val profile = Profile("", "")
    val validation = Profile.validateProfile(profile)
    assert(validation.isFailure)
    println(validation)

  }

  test("valid profile") {
    val profile = Profile("Barney Rebel", "barney.rebel@gmail.com")
    val validation = Profile.validateProfile(profile)
    assert(validation.isSuccess)
    println(validation)
  }
}