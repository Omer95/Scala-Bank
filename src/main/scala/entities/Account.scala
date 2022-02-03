package main.scala.entities

class Account(c: Customer, p: Product, b: Int) {
  val customer: Customer = c
  val product: Product = p
  private var balance: Int = b

  def deposit(amount: Int): Unit = {
    println(s"Depositing $amount to $customer's account")
    balance += amount
  }

  def withdraw(amount: Int): Unit = {
    println(s"Withdrawing $amount from $customer's' account")
    balance -= amount
  }

  override def toString = s"$customer with $product has remaining balance: $balance"

}
