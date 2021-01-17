package pl.karol202.smartwallet.domain.entity

import java.time.LocalDate

sealed class NewTransaction
{
	data class Expense(override val date: LocalDate,
	                   val amount: Double) : NewTransaction()

	data class Income(override val date: LocalDate,
	                  val amount: Double) : NewTransaction()

	abstract val date: LocalDate
}
