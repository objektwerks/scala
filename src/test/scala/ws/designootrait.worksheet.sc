import java.time.LocalDateTime

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

case class Account(accountType: AccountType, number: Int, balance: Amount) extends Transaction

trait Transaction { account: Account =>
  def deposit(amount: Amount): Deposited = {
    Deposited(copy(balance = balance + amount), amount, LocalDateTime.now)
  }
  def transfer(to: Account, amount: Amount): Transferred = {
    Transferred(copy(balance = balance - amount), to.copy(balance = to.balance + amount), amount, LocalDateTime.now)
  }
  def withdraw(amount: Amount): Withdrawn = {
    Withdrawn(copy(balance = balance - amount), amount, LocalDateTime.now)
  }
}

case class Deposited(to: Account, amount: Amount, on: LocalDateTime)
case class Transferred(from: Account, to: Account, amount: Amount, on: LocalDateTime)
case class Withdrawn(from: Account, amount: Amount, on: LocalDateTime)

val checking = Account(Checking, 1, Amount(100.0))
val savings = Account(Savings, 2, Amount(100.0))
val member = Member(1, Set(checking, savings))
val bank = Bank(1, Set(member))

val deposited = checking.deposit(Amount(10.00))
val transferred = checking.transfer(savings, Amount(10.00))
val withdrawn = savings.withdraw(Amount(10.00))