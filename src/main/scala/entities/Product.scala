package main.scala.entities

import java.util.UUID

abstract class Product {
  val id: UUID = UUID.randomUUID()
  val name: String

  override def toString: String = s"Product= $name"
}

/*--------------------------Deposits Products--------------------------*/
abstract class Deposits extends Product {
  val interestPerYear: Double
  val minimumBalancePerMonth: Dollars
}

abstract class Checking extends Deposits

abstract class Savings extends Deposits {
  val transactionsAllowedPerMonth: Int
}

/*---------Checking Products----------*/
class CoreChecking(val minimumBalancePerMonth: Dollars,
                   val interestPerYear: Double) extends Checking {
  println("Create Core Checking Product")
  override val name: String = "Core Checking"
}

class StudentChecking(val minimumBalancePerMonth: Dollars,
                      val interestPerYear: Double) extends Checking {
  println("Created Student Checking Product")
  override val name: String = "Student Checking"
}

/*---------Savings Products------------*/
class RewardsSavings(val minimumBalancePerMonth: Dollars,
                     val interestPerYear: Double,
                     val transactionsAllowedPerMonth: Int) extends Savings {
  println("Created Rewards Savings Product")
  override val name: String = "Rewards Savings"
}

/*---------Lending Products------------*/
abstract class Lending extends Product {
  val annualFee: Double
  val apr: Double
  val rewardsPercent: Double
}

class CreditCard(val annualFee: Double, val apr: Double, val rewardsPercent: Double) extends Lending {
  println("Created Credit Card Product")
  override val name: String = "Credit Card"
}