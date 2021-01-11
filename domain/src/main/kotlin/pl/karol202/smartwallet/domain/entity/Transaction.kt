package pl.karol202.smartwallet.domain.entity

sealed class Transaction
{
	data class Expense(override val id: Long,
	                   val amount: Double) : Transaction()

	data class Income(override val id: Long,
	                  val amount: Double) : Transaction()

	abstract val id: Long
}
