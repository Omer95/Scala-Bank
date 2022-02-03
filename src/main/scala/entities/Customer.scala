package main.scala.entities

import java.time.LocalDate

class Customer(val first: String, val last: String, val email: Email, val dateOfBirth: LocalDate) {

  override def toString: String = s"$first $last => $email"

}
