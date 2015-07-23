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

  def validate(profile: Profile): ValidationNel[String, Profile] = {
    (validateName(profile.name).toValidationNel |@| validateEmail(profile.email).toValidationNel) {
      Profile(_, _)
    }
  }

  def isValid(validation: ValidationNel[String, Profile]): Boolean = {
    validation match {
      case Success(s) => true
      case Failure(f) => false
    }
  }
}

class ValidationTest extends FunSuite {
  test("valid profile") {
    val profile = Profile("Barney Rebel", "barney.rebel@gmail.com")
    val validationNel = Profile.validate(profile)
    assert(Profile.isValid(validationNel))
  }

  test("invalid profile") {
    val invalidProfile = Profile("", "")
    val validationNel = Profile.validate(invalidProfile)
    assert(!Profile.isValid(validationNel))
  }
}