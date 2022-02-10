package main.scala

import main.scala.entities._
import java.util.UUID

import scala.util.Random

object BankOfScala {
  def main(args: Array[String]): Unit = {
    println("Opening Bank!")

    val bank = new Bank(name = "Scala Bank", country = "United Kingdom", city = "London",
      email = Email("info", "scalabank.com"))

    val customerIds = getCustomers map { c => bank.createNewCustomer(c._1, c._2, c._3, c._4) }
    val depositProductIds = getDepositProducts map { p => bank.addNewDepositProduct(p._1, p._2, p._3) }
    val lendingProductIds = getLendingProducts map { l => bank.addNewLendingProduct(l._2, l._3, l._4) }

    /* Logging */
    println(
      s"""
        |Bank: $bank\n
        |Customer IDs: $customerIds\n
        |Deposit Product IDs: $depositProductIds\n
        |Lending Product IDs: $lendingProductIds
        |""".stripMargin
    )

    def openAccounts(customerId: UUID, productId: UUID, productType: String) = productType match {
      case "Deposits" => bank.openDepositAccount(customerId, productId, _: Dollars)
      case "Lending" => bank.openLendingAccount(customerId, productId, _: Dollars)
    }

    /*
    Bank clerk opening the account.
    There is no money deposited in the account yet, so the accounts are not active
     */
    val depositAccounts = for (c <- customerIds; p <- depositProductIds) yield openAccounts(c, p, "Deposits")
    /* Depositing money into the accounts */
    val random = new scala.util.Random()
    val depositAccountIds = depositAccounts map { account => account(Dollars(10000 + random.nextInt(10000))) }

    /* Logging */
    println(
      s"""
        |Deposit Accounts: $depositAccounts\n
        |Deposit Account IDs: $depositAccountIds
        |""".stripMargin
    )

    /*
    Open Credit Card accounts.
    The bank has not finished the credit check, so balance will be known later
     */
    val lendingAccounts = for (c <- customerIds; p <- lendingProductIds) yield openAccounts(c, p, "Lending")
    val lendingAccountIds = lendingAccounts map (account => account(Dollars(random.nextInt(500))))

    /*
    Logging
     */
    println(
      s"""
         |Lending Accounts: $lendingAccounts\n
         |Lending Account IDs: $lendingAccountIds\n
         |Bank: $bank
         |""".stripMargin
    )

    /*
    Performing deposit account transactions
     */
    val randomAmount = new Random(100)
    depositAccountIds foreach { accountId =>
      bank deposit(accountId, Dollars(1 + randomAmount.nextInt(100)))
      bank withdraw(accountId, Dollars(1 + randomAmount.nextInt(50)))
    }

    /*
    Performing lending account transactions
     */
    lendingAccountIds foreach {
      bank useCreditCard(_, Dollars(1 + randomAmount.nextInt(500)))
      bank payCreditCardBill(_, Dollars(1 + randomAmount.nextInt(100)))
    }
  }
  /* ------------- Data ---------------*/
  def getCustomers: Seq[(String, String, String, String)] = {
    Seq(
      ("Omer", "Ahmed", "omer@gmail.com", "1995/06/01"),
      ("Ali", "Ahmed", "ali@gmail.com", "1997/09/22"),
      ("Hania", "Omer", "hania@gmail.com", "1997/12/06")
    )
  }

  def getDepositProducts: Seq[(String, Int, Double)] = {
    Seq(
      ("CoreChecking", 1000, 0.025),
      ("StudentChecking", 0, 0.010),
      ("RewardsSavings", 10000, 0.10)
    )
  }

  def getLendingProducts: Seq[(String, Double, Double, Double)] = {
    Seq(("CreditCard", 99.0, 14.23, 20.00))
  }
}
