package main.scala.services

import java.util.UUID
import main.scala.entities.{CreditCard, DepositsAccount, Dollars, LendingAccount}

trait AccountService extends AccountsDb with CustomerService with ProductService {
  def OpenDepositAccount(customerId: UUID, productId: UUID, amount: Dollars): UUID = {
    require(getCustomer(customerId).nonEmpty, s"no customer found with id: $customerId")

    val maybeProduct = getDepositProduct(productId)
    require(maybeProduct.nonEmpty, s"no deposit product found with id: $productId")

    val account = new DepositsAccount(getCustomer(customerId).get, maybeProduct.get, amount)
    saveDepositsAccount(account)
    account.id
  }

  def OpenLendingAccount(customerId: UUID, productId: UUID, balance: Dollars = Dollars.Zero): UUID = {
    require(getCustomer(customerId).nonEmpty, s"no customer found with id: $customerId")

    val maybeProduct = getLendingProduct(productId)
    require(maybeProduct.nonEmpty, s"no product found with id: $productId")

    val account = new LendingAccount(getCustomer(customerId).get, maybeProduct.get, balance)
    saveLendingAccount(account)
    account.id
  }

  def deposit(accountId: UUID, amount: Dollars): Unit = {
    val maybeAccount = getDepositsAccount(accountId)
    require(maybeAccount.nonEmpty, "A valid deposit account ID must be used")
    maybeAccount.get deposit amount
  }

  def withdraw(accountId: UUID, amount: Dollars): Unit = {
    val maybeAccount = getDepositsAccount(accountId)
    require(maybeAccount.nonEmpty, "A valid deposit account ID must be used")
    maybeAccount.get withdraw amount
  }

  def useCreditCard(accountId: UUID, amount: Dollars): Unit = {
    val maybeAccount = getLendingAccount(accountId)
    require(maybeAccount.nonEmpty, "A valid lending account ID must be used")
    maybeAccount.get withdraw amount
  }

  def payCreditCardBill(accountId: UUID, amount: Dollars): Unit = {
    val maybeAccount = getLendingAccount(accountId)
    require(maybeAccount.nonEmpty, "A valid lending account ID must be used")
    maybeAccount.get payBill amount
  }
}
