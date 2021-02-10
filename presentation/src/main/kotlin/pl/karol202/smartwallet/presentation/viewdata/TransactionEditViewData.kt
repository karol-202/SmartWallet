package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.entity.asId
import java.time.LocalDate

sealed class TransactionEditViewData
{
	data class Expense(override val subcategoryId: String,
	                   override val date: LocalDate,
	                   val accountId: String,
	                   val amount: Double) : TransactionEditViewData()
	{
		override val type = TransactionTypeViewData.EXPENSE

		override fun toExpense() = this

		override fun toIncome() = Income(subcategoryId, date, accountId, amount)

		override fun withSubcategoryId(subcategoryId: String) = copy(subcategoryId = subcategoryId)

		override fun withDate(date: LocalDate) = copy(date = date)

		fun withAccountId(accountId: String) = copy(accountId = accountId)

		fun withAmount(amount: Double) = copy(amount = amount)
	}

	data class Income(override val subcategoryId: String,
	                  override val date: LocalDate,
	                  val accountId: String,
	                  val amount: Double) : TransactionEditViewData()
	{
		override val type = TransactionTypeViewData.INCOME

		override fun toExpense() = Expense(subcategoryId, date, accountId, amount)

		override fun toIncome() = this

		override fun withSubcategoryId(subcategoryId: String) = copy(subcategoryId = subcategoryId)

		override fun withDate(date: LocalDate) = copy(date = date)

		fun withAccountId(accountId: String) = copy(accountId = accountId)

		fun withAmount(amount: Double) = copy(amount = amount)
	}

	abstract val type: TransactionTypeViewData
	abstract val subcategoryId: String
	abstract val date: LocalDate

	abstract fun toExpense(): Expense

	abstract fun toIncome(): Income

	abstract fun withSubcategoryId(subcategoryId: String): TransactionEditViewData

	abstract fun withDate(date: LocalDate): TransactionEditViewData
}

fun Transaction<Existing>.toEditViewData() = when(this)
{
	is Transaction.Expense -> TransactionEditViewData.Expense(subcategoryId, date, accountId, amount)
	is Transaction.Income -> TransactionEditViewData.Income(subcategoryId, date, accountId, amount)
}

fun TransactionEditViewData.toEntity() = when(this)
{
	is TransactionEditViewData.Expense -> Transaction.Expense(New, subcategoryId, date, accountId, amount)
	is TransactionEditViewData.Income -> Transaction.Income(New, subcategoryId, date, accountId, amount)
}

fun TransactionEditViewData.toEntity(id: String) = when(this)
{
	is TransactionEditViewData.Expense -> Transaction.Expense(id.asId(), subcategoryId, date, accountId, amount)
	is TransactionEditViewData.Income -> Transaction.Income(id.asId(), subcategoryId, date, accountId, amount)
}
