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

trait Transaction[A, B] {
  def deposit(to: A, amount: B): A
  def transfer(from: A, to: A, amount: B): (A, A)
  def withdraw(from: A, amount: B): A
}

object DefaultTransaction extends Transaction[Account, Amount] {
  def deposit(to: Account, amount: Amount): Account = to.copy(balance = to.balance + amount)
  def transfer(from: Account, to: Account, amount: Amount): (Account, Account) = {
    (from.copy(balance = from.balance - amount), to.copy(balance = to.balance + amount))
  }
  def withdraw(from: Account, amount: Amount): Account = from.copy(balance = from.balance - amount)
}

def executeUnitOfWork[A, B](checking: Account,
                            savings: Account,
                            transaction: Transaction[Account, Amount]): (Account, (Account, Account), Account) = {
  import transaction._
  val deposited = deposit(checking, Amount(10.00))
  val transferred = transfer(checking, savings, Amount(10.00))
  val withdrawn = withdraw(checking, Amount(10.00))
  (deposited, transferred, withdrawn)
}

val checking = Account(Checking, 1, Amount(100.0))
val savings = Account(Savings, 2, Amount(100.0))
val member = Member(1, Set(checking, savings))
val bank = Bank(1, Set(member))

val transaction = executeUnitOfWork(checking, savings, DefaultTransaction)
transaction._1
transaction._2
transaction._3