package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.entity.asId
import java.time.LocalDate

sealed class TransactionEditViewData
{
	data class Expense(override val date: LocalDate,
	                   val amount: Double) : TransactionEditViewData()
	{
		override val type = TransactionTypeViewData.EXPENSE

		override fun toExpense() = this

		override fun toIncome() = Income(date, amount)

		override fun withDate(date: LocalDate) = copy(date = date)

		fun withAmount(amount: Double) = copy(amount = amount)
	}

	data class Income(override val date: LocalDate,
	                  val amount: Double) : TransactionEditViewData()
	{
		override val type = TransactionTypeViewData.INCOME

		override fun toExpense() = Expense(date, amount)

		override fun toIncome() = this

		override fun withDate(date: LocalDate) = copy(date = date)

		fun withAmount(amount: Double) = copy(amount = amount)
	}

	abstract val type: TransactionTypeViewData
	abstract val date: LocalDate

	abstract fun toExpense(): Expense

	abstract fun toIncome(): Income

	abstract fun withDate(date: LocalDate): TransactionEditViewData
}

fun Transaction<Existing>.toEditViewData() = when(this)
{
	is Transaction.Expense -> TransactionEditViewData.Expense(date, amount)
	is Transaction.Income -> TransactionEditViewData.Income(date, amount)
}

fun TransactionEditViewData.toEntity() = when(this)
{
	is TransactionEditViewData.Expense -> Transaction.Expense(New, date, amount)
	is TransactionEditViewData.Income -> Transaction.Income(New, date, amount)
}

fun TransactionEditViewData.toEntity(id: String) = when(this)
{
	is TransactionEditViewData.Expense -> Transaction.Expense(id.asId(), date, amount)
	is TransactionEditViewData.Income -> Transaction.Income(id.asId(), date, amount)
}
