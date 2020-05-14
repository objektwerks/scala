import java.time.LocalDateTime

import scala.language.implicitConversions

case class Bank(number: Int, members: Set[Member])
case class Member(number: Int, accounts: Set[Account])

case class Amount(amount: Double) {
  implicit def +(other: Amount): Amount = Amount(amount + other.amount)
  implicit def -(other: Amount): Amount = Amount(amount - other.amount)
  implicit def ++(amounts: List[Amount]): Amount = amounts.fold(Amount(0.0))(_ + _)
}

sealed trait AccountType
case object Checking extends AccountType
case object Savings extends AccountType

case class Account(accountType: AccountType, number: Int, balance: Amount)

sealed trait Command
case class Deposit(to: Account, amount: Amount) extends Command
case class Transfer(from: Account, to: Account, amount: Amount) extends Command
case class Withdraw(from: Account, amount: Amount) extends Command

sealed trait Event
case class Deposited(to: Account, amount: Amount, on: LocalDateTime) extends Event
case class Transferred(from: Account, to: Account, amount: Amount, on: LocalDateTime) extends Event
case class Withdrawn(from: Account, amount: Amount, on: LocalDateTime) extends Event

object Transaction {
  def execute(command: Command): Event = command match {
    case deposit: Deposit =>
      Deposited(deposit.to.copy(balance = deposit.to.balance + deposit.amount), deposit.amount, LocalDateTime.now)
    case transfer: Transfer =>
      Transferred(transfer.from.copy(balance = transfer.from.balance - transfer.amount),
        transfer.to.copy(balance = transfer.to.balance + transfer.amount),
        transfer.amount, LocalDateTime.now)
    case withdraw: Withdraw =>
      Withdrawn(withdraw.from.copy(balance = withdraw.from.balance - withdraw.amount), withdraw.amount, LocalDateTime.now)
  }
}

val checking = Account(Checking, 1, Amount(100.0))
val savings = Account(Savings, 2, Amount(100.0))
val member = Member(1, Set(checking, savings))
val bank = Bank(1, Set(member))

import Transaction._
val deposited = execute(Deposit(checking, Amount(10.00)))
val transferred = execute(Transfer(checking, savings, Amount(10.00)))
val withdrawn = execute(Withdraw(checking, Amount(10.00)))
