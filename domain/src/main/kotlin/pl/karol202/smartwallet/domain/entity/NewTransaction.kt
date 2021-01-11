package pl.karol202.smartwallet.domain.entity

sealed class NewTransaction
{
	data class Expense(val amount: Double) : NewTransaction()

	data class Income(val amount: Double) : NewTransaction()
}
