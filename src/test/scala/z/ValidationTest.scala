package z

import scalaz.Scalaz._
import scalaz._

import org.scalatest.FunSuite

case class Profile(name: String, email: String)

object Profile {
  def validateName(name: String): Validation[String, String] = {
    if (name.isEmpty) "Please, enter your name.".failure else name.success
  }

  def validateEmail(email: String): Validation[String, String] = {
    if (email.isEmpty) "Please, enter your email.".failure else email.success
  }

  def validate(profile: Profile) : ValidationNel[String, Profile] = {
    (validateName(profile.name).toValidationNel |@| validateEmail(profile.email).toValidationNel) {
      Profile(_, _)
    }
  }

  def validate(validation: ValidationNel[String, Profile]): Unit = {
    validation match {
      case Success(s) =>
        assert(validation.isSuccess)
        persist(s)
      case Failure(f) =>
        assert(validation.isFailure)
        assert(f.toList.size == 2)
        println(s"Profile is invalid: $f")
        println("Invalid profile as list: " + f.toList)
    }
  }

  def persist(profile: Profile): Unit = {
    println(s"Profile persisted: $profile")
  }
}

class ValidationTest extends FunSuite {
  test("valid profile") {
    val profile = Profile("Barney Rebel", "barney.rebel@gmail.com")
    val isProfileValid = Profile.validate(profile)
    Profile.validate(isProfileValid)
  }

  test("invalid profile") {
    val profile = Profile("", "")
    val isProfileValid = Profile.validate(profile)
    Profile.validate(isProfileValid)
  }
}