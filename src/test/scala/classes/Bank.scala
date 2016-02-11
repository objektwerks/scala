package classes

import scala.util.Try

object Bank {
  def enquiry(number: String): Option[Account] = None
  def deposit(credit: Account, amount: Double): Try[Transaction] = Try(Deposit(credit, amount))
  def withdrawl(debit: Account, amount: Double): Try[Transaction] = Try(Withdrawl(debit, amount))
  def transfer(debit: Account, credit: Account, amount: Double): Try[Transaction] = Try(Transfer(debit, credit, amount))
  implicit def orderAccountsByName: Ordering[Account] = Ordering.by(_.name)
}
case class Bank(number: String, name: String, email: String)
case class Account(number: String, name: String, email: String, balance: Double)

trait Transaction
case class Deposit(credit: Account, amount: Double) extends Transaction
case class Withdrawl(debit: Account, amount: Double) extends Transaction
case class Transfer(debit: Account, credit: Account, ammount: Double) extends Transaction