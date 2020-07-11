import java.time.LocalDateTime

import scala.language.implicitConversions

case class Bank(number: Int, members: Set[Member])
case class Member(number: Int, accounts: Set[Account])

case class Amount(amount: Double) {
  implicit def +(other: Amount): Amount = Amount(amount + other.amount)
  implicit def -(other: Amount): Amount = Amount(amount - other.amount)
  implicit def ++(amounts: List[Amount]): Amount = amounts.fold(Amount(0.0))(_ + _)
}

abstract class Account {
  def number: Int
  var balance: Amount
  def deposit(amount: Amount): Deposited = {
    balance = balance + amount
    Deposited(this, amount, LocalDateTime.now)
  }
  def transfer(amount: Amount, to: Account): Transferred = {
    balance = balance - amount
    to.balance = to.balance + amount
    Transferred(this, to, amount, LocalDateTime.now)
  }
  def withdraw(amount: Amount): Withdrawn = {
    balance = balance - amount
    Withdrawn(this, amount, LocalDateTime.now)
  }
}
case class Checking(override val number: Int, var balance: Amount) extends Account
case class Savings(override val number: Int, var balance: Amount) extends Account

case class Deposited(to: Account, amount: Amount, on: LocalDateTime)
case class Transferred(from: Account, to: Account, amount: Amount, on: LocalDateTime)
case class Withdrawn(from: Account, amount: Amount, on: LocalDateTime)

val checking = Checking(1, Amount(100.0))
val savings = Savings(2, Amount(100.0))
val member = Member(1, Set(checking, savings))
val bank = Bank(1, Set(member))

val deposited = checking.deposit(Amount(10.00))
val transferred = checking.transfer(Amount(10.00), savings)
val withdrawn = savings.withdraw(Amount(10.00))