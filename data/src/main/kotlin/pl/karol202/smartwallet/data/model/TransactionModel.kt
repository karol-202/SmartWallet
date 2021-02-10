package pl.karol202.smartwallet.data.model

import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.entity.asId
import java.time.LocalDate

sealed class TransactionModel
{
	data class Expense(override val id: String,
	                   override val subcategoryId: String,
	                   override val date: LocalDate,
	                   val accountId: String,
	                   val amount: Double) : TransactionModel()

	data class Income(override val id: String,
	                  override val subcategoryId: String,
	                  override val date: LocalDate,
	                  val accountId: String,
	                  val amount: Double) : TransactionModel()

	abstract val id: String
	abstract val subcategoryId: String
	abstract val date: LocalDate
}

fun Transaction<New>.toModel(newId: String) = when(this)
{
	is Transaction.Expense -> TransactionModel.Expense(newId, subcategoryId, date, accountId, amount)
	is Transaction.Income -> TransactionModel.Income(newId, subcategoryId, date, accountId, amount)
}

fun Transaction<Existing>.toModel() = when(this)
{
	is Transaction.Expense -> TransactionModel.Expense(id.value, subcategoryId, date, accountId, amount)
	is Transaction.Income -> TransactionModel.Income(id.value, subcategoryId, date, accountId, amount)
}

fun TransactionModel.toEntity() = when(this)
{
	is TransactionModel.Expense -> Transaction.Expense(id.asId(), subcategoryId, date, accountId, amount)
	is TransactionModel.Income -> Transaction.Income(id.asId(), subcategoryId, date, accountId, amount)
}
