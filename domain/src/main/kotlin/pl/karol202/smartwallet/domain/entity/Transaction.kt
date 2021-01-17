package pl.karol202.smartwallet.domain.entity

import java.time.LocalDate

sealed class Transaction
{
	data class Expense(override val id: Long,
	                   override val date: LocalDate,
	                   val amount: Double) : Transaction()

	data class Income(override val id: Long,
	                  override val date: LocalDate,
	                  val amount: Double) : Transaction()

	abstract val id: Long
	abstract val date: LocalDate
}
