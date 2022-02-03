package main.scala.entities

abstract class Product {
  val name: String

  override def toString: String = s"Product= $name"
}

/*--------------------------Deposits Products--------------------------*/
abstract class Deposits extends Product {
  val interestPerYear: Double
  val minimumBalancePerMonth: Int
}

abstract class Checking extends Deposits

abstract class Savings extends Deposits {
  val transactionsAllowedPerMonth: Int
}

/*---------Checking Products----------*/
class CoreChecking(bal: Int, rate: Double) extends Checking {
  println("Created Core Checking Product")
  override val interestPerYear: Double = rate
  override val minimumBalancePerMonth: Int = bal
  override val name: String = "Core Checking"
}

class StudentChecking(bal: Int, rate: Double) extends Checking {
  println("Created Student Checking Product")
  override val interestPerYear: Double = rate
  override val minimumBalancePerMonth: Int = bal
  override val name: String = "Student Checking"
}

/*---------Savings Products------------*/
class RewardsSavings(bal: Int, rate: Double, trans: Int) extends Savings {
  println("Created Rewards Savings Product")
  override val interestPerYear: Double = rate
  override val minimumBalancePerMonth: Int = bal
  override val transactionsAllowedPerMonth: Int = trans
  override val name: String = "Rewards Savings"
}

/*---------Lending Products------------*/
abstract class Lending extends Product {
  val annualFee: Double
  val apr: Double
  val rewardsPercent: Double
}

class CreditCard(fee: Double, rate: Double, pct: Double) extends Lending {
  println("Created Credit Card Product")
  override val annualFee: Double = fee
  override val apr: Double = rate
  override val rewardsPercent: Double = pct
  override val name: String = "Credit Card"
}