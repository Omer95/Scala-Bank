package main.scala.entities

object Dollars {
  def apply(a: Int): Dollars = new Dollars(a)
}

class Dollars(val amount: Int) extends AnyVal {
  def +(value: Dollars): Dollars = new Dollars(amount + value.amount)

  def -(value: Dollars): Dollars = new Dollars(amount - value.amount)

  def >(value: Dollars): Boolean = amount > value.amount

  override def toString: String = "$" + amount

}
