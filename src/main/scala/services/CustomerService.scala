package main.scala.services

import java.time.LocalDate
import java.util.UUID
import main.scala.entities.{Customer, Email}

trait CustomerService extends CustomerDb {
  def createNewCustomer(first: String, last: String, email: String, dateOfBirth: String): UUID = {
    def getEmail: Email = {
      val Array(value, domain) = email.split("@")
      Email(value, domain)
    }
    def getDateOfBirth: LocalDate = {
      val Array(year, month, day) = dateOfBirth.split("/")
      LocalDate.of(year.toInt, month.toInt, day.toInt)
    }

    val customer = new Customer(first, last, getEmail, getDateOfBirth)
    saveCustomer(customer)
    customer.id
  }
}
