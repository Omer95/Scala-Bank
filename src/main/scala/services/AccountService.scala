package main.scala.services

import java.util.UUID
import main.scala.entities.{DepositsAccount, Dollars, LendingAccount}

trait AccountService extends AccountsDb with CustomerService with ProductService
