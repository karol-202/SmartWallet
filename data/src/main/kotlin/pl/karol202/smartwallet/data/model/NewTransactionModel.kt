package pl.karol202.smartwallet.data.model

import pl.karol202.smartwallet.domain.entity.NewTransaction

sealed class NewTransactionModel
{
	data class Expense(val amount: Double) : NewTransactionModel()

	data class Income(val amount: Double) : NewTransactionModel()
}

fun NewTransaction.toModel() = when(this)
{
	is NewTransaction.Expense -> NewTransactionModel.Expense(amount)
	is NewTransaction.Income -> NewTransactionModel.Income(amount)
}
