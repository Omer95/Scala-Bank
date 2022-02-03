package main.scala

import java.time.LocalDate
import main.scala.entities._

object BankOfScala {
  def main(args: Array[String]): Unit = {
    println("Instantiating Bank!")

    val coreChecking = new CoreChecking(Dollars(1000), 0.025)
    val studentChecking = new StudentChecking(Dollars(0), 0.010)
    val rewardsSavings = new RewardsSavings(Dollars(10000), 0.10, 1)
    val creditCard = new CreditCard(99.0, 14.50, 20.00)
    val products = Set(coreChecking, studentChecking, rewardsSavings, creditCard)

    val omerAhmed = new Customer("Omer", "Ahmed", Email("omer", "gmail.com"), LocalDate.of(1995, 6, 1))
    val omerCheckingAccount = new DepositsAccount(omerAhmed, coreChecking, Dollars(1000))
    val omerSavingsAccount = new DepositsAccount(omerAhmed, rewardsSavings, Dollars(20000))
    val omerCreditAccount = new LendingAccount(omerAhmed, creditCard, Dollars(4500))
    val accounts = Set(omerCheckingAccount, omerSavingsAccount, omerCreditAccount)

    val bank = new Bank("Scala Bank", "London", "United Kingdom", Email("info", "scalabank.com"), products, Set(omerAhmed), accounts)

    println(omerAhmed)

    omerCheckingAccount deposit 100
    println(omerCheckingAccount)

    omerCheckingAccount withdraw 150
    println(omerCheckingAccount)
  }
}
