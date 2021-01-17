package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.NewTransaction
import pl.karol202.smartwallet.domain.entity.Transaction

sealed class TransactionEditViewData
{
	data class Expense(val amount: Double) : TransactionEditViewData()
	{
		override val type = TransactionTypeViewData.EXPENSE

		override fun toExpense() = this

		override fun toIncome() = Income(amount)

		fun withAmount(amount: Double) = copy(amount = amount)
	}

	data class Income(val amount: Double) : TransactionEditViewData()
	{
		override val type = TransactionTypeViewData.INCOME

		override fun toExpense() = Expense(amount)

		override fun toIncome() = this

		fun withAmount(amount: Double) = copy(amount = amount)
	}

	abstract val type: TransactionTypeViewData

	abstract fun toExpense(): Expense

	abstract fun toIncome(): Income
}

fun Transaction.toEditViewData() = when(this)
{
	is Transaction.Expense -> TransactionEditViewData.Expense(amount)
	is Transaction.Income -> TransactionEditViewData.Income(amount)
}

fun TransactionEditViewData.toEntity() = when(this)
{
	is TransactionEditViewData.Expense -> NewTransaction.Expense(amount)
	is TransactionEditViewData.Income -> NewTransaction.Income(amount)
}

fun TransactionEditViewData.toEntity(id: Long) = when(this)
{
	is TransactionEditViewData.Expense -> Transaction.Expense(id, amount)
	is TransactionEditViewData.Income -> Transaction.Income(id, amount)
}
