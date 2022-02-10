package main.scala.entities

import java.util.UUID
import Dollars.Zero

abstract class Account {
  val id: UUID = UUID.randomUUID()
  val customer: Customer
  val product: Product
  def getBalance: Dollars
}

class DepositsAccount(val customer: Customer, val product: Deposits,
                      private var balance: Dollars) extends Account {

  def deposit(amount: Dollars): Unit = {
    require(amount > Zero, "amount deposited should be greater than 0")
    balance += amount
    println(s"Deposited $amount to ${this.toString}")
  }

  def withdraw(amount: Dollars): Unit = {
    require(amount > Zero && balance > amount, "amount should be greater than 0 and balance should be greater than amount")
    balance -= amount
    println(s"Withdrawn $amount from ${this.toString}")
  }

  override def getBalance: Dollars = balance

  override def toString = s"customer: $customer, product: $product, balance: $balance"

}

class LendingAccount(val customer: Customer, val product: Lending,
                     private var balance: Dollars) extends Account {
  def payBill(amount: Dollars): Unit = {
    require(amount > Zero, "The payment amount must be greater than 0")
    balance += amount
    println(s"Paid bill: $amount against ${this.toString}")
  }

  def withdraw(amount: Dollars): Unit = {
    require(amount > Zero, "The withdrawal amount must be greater than 0")
    balance -= amount
    println(s"Debited $amount from ${this.toString}")
  }

  override def getBalance: Dollars = balance

  override def toString: String = s"customer: $customer, product: $product, balance of $balance"
}
