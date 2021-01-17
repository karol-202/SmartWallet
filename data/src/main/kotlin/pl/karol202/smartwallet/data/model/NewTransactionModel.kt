package pl.karol202.smartwallet.data.model

import pl.karol202.smartwallet.domain.entity.NewTransaction
import java.time.LocalDate

sealed class NewTransactionModel
{
	data class Expense(override val date: LocalDate,
	                   val amount: Double) : NewTransactionModel()

	data class Income(override val date: LocalDate,
	                  val amount: Double) : NewTransactionModel()

	abstract val date: LocalDate
}

fun NewTransaction.toModel() = when(this)
{
	is NewTransaction.Expense -> NewTransactionModel.Expense(date, amount)
	is NewTransaction.Income -> NewTransactionModel.Income(date, amount)
}
