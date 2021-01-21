package pl.karol202.smartwallet.domain.entity

import java.time.LocalDate

sealed class Transaction<I : Id>
{
	data class Expense<I : Id>(override val id: I,
	                           override val subcategoryId: String,
	                           override val date: LocalDate,
	                           val amount: Double) : Transaction<I>()

	data class Income<I : Id>(override val id: I,
	                          override val subcategoryId: String,
	                          override val date: LocalDate,
	                          val amount: Double) : Transaction<I>()

	abstract val id: I
	abstract val subcategoryId: String
	abstract val date: LocalDate
}
