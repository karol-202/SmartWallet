package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Transaction

sealed class TransactionEditViewData
{
	data class Expense(val amount: Double) : TransactionEditViewData()
	{
		override fun toExpense() = this

		override fun toIncome() = Income(amount)

		fun withAmount(amount: Double) = copy(amount = amount)
	}

	data class Income(val amount: Double) : TransactionEditViewData()
	{
		override fun toExpense() = Expense(amount)

		override fun toIncome() = this

		fun withAmount(amount: Double) = copy(amount = amount)
	}

	abstract fun toExpense(): Expense

	abstract fun toIncome(): Income
}

fun Transaction.toEditViewData() = when(this)
{
	is Transaction.Expense -> TransactionEditViewData.Expense(amount)
	is Transaction.Income -> TransactionEditViewData.Income(amount)
}
