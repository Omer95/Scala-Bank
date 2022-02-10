package main.scala.entities

object Dollars {
  val Zero = new Dollars(0)
  def apply(a: Int): Dollars = new Dollars(a)
}

class Dollars(val amount: Int) extends AnyVal with Ordered[Dollars]{

  override def compare(that: Dollars): Int = amount - that.amount

  def +(value: Dollars): Dollars = new Dollars(amount + value.amount)

  def -(value: Dollars): Dollars = new Dollars(amount - value.amount)

  override def toString: String = "$" + amount

}
