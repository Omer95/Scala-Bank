package main.scala.entities

import java.time.LocalDate
import java.util.UUID

class Bank(val name: String,
           val city: String,
           val country: String,
           val email: Email) {

  private var depositProducts: Map[UUID, Deposits] = Map.empty
  private var lendingProducts: Map[UUID, Lending] = Map.empty
  private var depositAccounts: Map[UUID, DepositsAccount] = Map.empty
  private var lendingAccounts: Map[UUID, LendingAccount] = Map.empty
  private var customers: Map[UUID, Customer] = Map.empty

  println(s"$name Established in 2022")

  /**
   * @param first       : first name of the customer
   * @param last        : last name of the customer
   * @param email       : email of the customer
   * @param dateOfBirth : date of birth of the customer in 'yyyy/mm/dd' format
   * @return the customer id for the new customer
   */
  def createNewCustomer(first: String, last: String, email: String, dateOfBirth: String): UUID = {

    def getEmail: Email = {
      val Array(value, domain) = email.split("@")
      Email(value, domain)
    }

    def getDateOfBirth: LocalDate = {
      val Array(year, month, day) = dateOfBirth.split("/")
      LocalDate.of(year.toInt, month.toInt, day.toInt)
    }

    customers.foreach(customer => {
      if (customer._2.email == getEmail) {
        println(s"customer with email address $getEmail is already a customer of this bank")
        return customer._1
      }
    })
    val customer = new Customer(first, last, getEmail, getDateOfBirth)
    customers += (customer.id -> customer)
    customer.id
  }

  /**
   * @param name                          : name of the product
   * @param minBalance                    : the minimum balance required for the product
   * @param ratePerYear                   : the rate of interest earned by the end of the year
   * @param transactionsAllowedPerMonth   : number of free transactions allowed for the product (optional)
   * @return the product id for the new product
   */
  def addNewDepositProduct(name: String, minBalance: Int, ratePerYear: Double,
                           transactionsAllowedPerMonth: Int = 2): UUID = {

    val product = name match {
      case "CoreChecking" => new CoreChecking(Dollars(minBalance), ratePerYear)
      case "StudentChecking" => new StudentChecking(Dollars(minBalance), ratePerYear)
      case "RewardsSavings" => new RewardsSavings(Dollars(minBalance), ratePerYear, transactionsAllowedPerMonth)
    }
    depositProducts += (product.id -> product)
    product.id
  }

  def addNewLendingProduct(annualFee: Double, apr: Double, rewardsPercent: Double): UUID = {
    val product = new CreditCard(annualFee, apr, rewardsPercent)
    lendingProducts += (product.id -> product)
    product.id
  }

  def openDepositAccount(customerId: UUID, productId: UUID, amount: Dollars): UUID = {
    require(customers.get(customerId).nonEmpty, s"no customer found with id=$customerId")
    require(depositProducts.get(productId).nonEmpty, s"no deposits product found with id=$productId")

    val account = new DepositsAccount(customers(customerId), depositProducts(productId), amount)
    depositAccounts += (account.id -> account)
    account.id
  }

  def openLendingAccount(customerId: UUID, productID: UUID, balance: Dollars = Dollars(0)): UUID = {
    require(customers.get(customerId).nonEmpty, s"no customer found with id=$customerId")
    require(lendingProducts.get(productID).nonEmpty, s"no lending product found with id=$productID")

    val account = new LendingAccount(customers(customerId), lendingProducts(productID), balance)
    lendingAccounts += (account.id -> account)
    account.id
  }

  def deposit(accountId: UUID, amount: Dollars): Unit = {
    require(depositAccounts.get(accountId).nonEmpty, s"A valid deposit account ID is required")
    depositAccounts(accountId) deposit amount
  }

  def withdraw(accountId: UUID, amount: Dollars): Unit = {
    require(depositAccounts.get(accountId).nonEmpty, s"A valid deposit account ID is required")
    depositAccounts(accountId) withdraw amount
  }

  def useCreditCard(accountId: UUID, amount: Dollars): Unit = {
    require(lendingAccounts.get(accountId).nonEmpty, s"A valid lending account ID is required")
    lendingAccounts(accountId) withdraw amount
  }

  def payCreditCardBill(accountId: UUID, amount: Dollars): Unit = {
    require(lendingAccounts.get(accountId).nonEmpty, s"A valid lending account ID is required")
    lendingAccounts(accountId) payBill amount
  }

  override def toString: String = s"[$name]" +
    s" - ${customers.size} customers" +
    s" - ${depositAccounts.size} deposit accounts" +
    s" - ${lendingAccounts.size} lending accounts"

}
