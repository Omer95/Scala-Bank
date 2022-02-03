package main.scala.entities

abstract class Account {
  val customer: Customer
  val product: Product
  def getBalance: Dollars
}

class DepositsAccount(val customer: Customer, val product: Deposits,
                      private var balance: Dollars) extends Account {

  def deposit(amount: Int): Unit = {
    require(amount > 0, "amount deposited should be greater than 0")
    println(s"Depositing $amount to $customer's account")
    balance += amount
  }

  def withdraw(amount: Int): Unit = {
    require(amount > 0 && balance > amount, "amount should be greater than 0 and balance should be greater than amount")
    println(s"Withdrawing $amount from $customer's' account")
    balance -= amount
  }

  override def getBalance: Dollars = balance

  override def toString = s"$customer with $product has remaining balance: $balance"

}

class LendingAccount(val customer: Customer, val product: Lending,
                     private var balance: Dollars) extends Account {
  def payBill(amount: Int): Unit = {
    require(amount > 0, "The payment amount must be greater than 0")
    println(s"Paying bill of $amount against $customer's account")
    balance += amount
  }

  def withdraw(amount: Int): Unit = {
    require(amount > 0, "The withdrawal amount must be greater than 0")
    println(s"debiting $amount from $customer's account'")
    balance -= amount
  }

  override def getBalance: Dollars = balance

  override def toString: String = s"$customer with $product has remaining balance of $balance"
}
