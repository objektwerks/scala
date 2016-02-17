package classes

import java.time.{LocalDateTime, LocalDate}

import scala.util.Try

object Bank {
  def list(): Set[Account] = Set[Account]()
  def enquiry(number: String): Option[Account] = None
  def deposit(amount: Amount, credit: Account): Try[Transaction] = Try(Deposit(LocalDateTime.now(), amount, credit))
  def withdrawl(amount: Amount, debit: Account): Try[Transaction] = Try(Withdrawl(LocalDateTime.now(), amount, debit))
  def transfer(amount: Amount, debit: Account, credit: Account): Try[Transaction] = Try(Transfer(LocalDateTime.now(), amount, debit, credit))
  implicit def ordering: Ordering[Bank] = Ordering.by(_.number)
}
case class Bank(number: String, accounts: Set[Account])

trait Account {
  def number: String
  def opened: LocalDate
  def closed: LocalDate
  def balance: Balance
  implicit def ordering: Ordering[Account] = Ordering.by(_.number)
}
case class CheckingAccount(number: String, opened: LocalDate, closed: LocalDate, balance: Balance) extends Account
case class SavingsAccount(number: String, opened: LocalDate, closed: LocalDate, balance: Balance) extends Account
case class MarketAccount(number: String, opened: LocalDate, closed: LocalDate, balance: Balance) extends Account

case class Balance(balance: Double) {
  implicit def +(other: Balance): Balance = Balance(balance + other.balance)
  implicit def -(other: Balance): Balance = Balance(balance - other.balance)
  implicit def ++(balances: List[Balance]): Balance = balances.foldLeft(Balance(0.0))(_ + _)
}

trait Transaction {
  def on: LocalDateTime
  def amount: Amount
  implicit def ordering: Ordering[Transaction] = Ordering.by(_.on)
}
case class Deposit(on: LocalDateTime, amount: Amount, credit: Account) extends Transaction
case class Withdrawl(on: LocalDateTime, amount: Amount, debit: Account) extends Transaction
case class Transfer(on: LocalDateTime, amount: Amount, debit: Account, credit: Account) extends Transaction

case class Amount(amount: Double) {
  implicit def +(other: Amount): Amount = Amount(amount + other.amount)
  implicit def -(other: Amount): Amount = Amount(amount - other.amount)
  implicit def ++(amounts: List[Amount]): Amount = amounts.foldLeft(Amount(0.0))(_ + _)
}