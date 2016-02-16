package classes

import java.time.{LocalDateTime, LocalDate}

import scala.util.Try

object Bank {
  def list(): Set[Account] = Set[Account]()
  def enquiry(number: String): Option[Account] = None
  def deposit(amount: Double, credit: Account): Try[Transaction] = Try(Deposit(LocalDateTime.now(), amount, credit))
  def withdrawl(amount: Double, debit: Account): Try[Transaction] = Try(Withdrawl(LocalDateTime.now(), amount, debit))
  def transfer(amount: Double, debit: Account, credit: Account): Try[Transaction] = Try(Transfer(LocalDateTime.now(), amount, debit, credit))
  implicit def ordering: Ordering[Bank] = Ordering.by(_.number)
}
case class Bank(number: String, accounts: Set[Account])

trait Account {
  def number: String
  def opened: LocalDate
  def closed: LocalDate
  def balance: Double
  implicit def ordering: Ordering[Account] = Ordering.by(_.number)
}
case class CheckingAccount(number: String, opened: LocalDate, closed: LocalDate, balance: Double) extends Account
case class SavingsAccount(number: String, opened: LocalDate, closed: LocalDate, balance: Double) extends Account
case class MarketAccount(number: String, opened: LocalDate, closed: LocalDate, balance: Double) extends Account

trait Transaction {
  def on: LocalDateTime
  def amount: Double
}
case class Deposit(on: LocalDateTime, amount: Double, credit: Account) extends Transaction
case class Withdrawl(on: LocalDateTime, amount: Double, debit: Account) extends Transaction
case class Transfer(on: LocalDateTime, amount: Double, debit: Account, credit: Account) extends Transaction