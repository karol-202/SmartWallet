package pl.karol202.smartwallet.data.model

import pl.karol202.smartwallet.domain.entity.Transaction

sealed class TransactionModel
{
	data class Expense(override val id: Long,
	                   val amount: Double) : TransactionModel()

	data class Income(override val id: Long,
	                  val amount: Double) : TransactionModel()

	abstract val id: Long
}

fun Transaction.toModel() = when(this)
{
	is Transaction.Expense -> TransactionModel.Expense(id, amount)
	is Transaction.Income -> TransactionModel.Income(id, amount)
}

fun TransactionModel.toEntity() = when(this)
{
	is TransactionModel.Expense -> Transaction.Expense(id, amount)
	is TransactionModel.Income -> Transaction.Income(id, amount)
}
