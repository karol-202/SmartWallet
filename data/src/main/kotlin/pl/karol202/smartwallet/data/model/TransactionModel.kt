package pl.karol202.smartwallet.data.model

import pl.karol202.smartwallet.domain.entity.Transaction
import java.time.LocalDate

sealed class TransactionModel
{
	data class Expense(override val id: Long,
	                   override val date: LocalDate,
	                   val amount: Double) : TransactionModel()

	data class Income(override val id: Long,
	                  override val date: LocalDate,
	                  val amount: Double) : TransactionModel()

	abstract val id: Long
	abstract val date: LocalDate
}

fun Transaction.toModel() = when(this)
{
	is Transaction.Expense -> TransactionModel.Expense(id, date, amount)
	is Transaction.Income -> TransactionModel.Income(id, date, amount)
}

fun TransactionModel.toEntity() = when(this)
{
	is TransactionModel.Expense -> Transaction.Expense(id, date, amount)
	is TransactionModel.Income -> Transaction.Income(id, date, amount)
}
