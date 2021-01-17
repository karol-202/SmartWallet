package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Transaction
import java.time.LocalDate

data class TransactionItemViewData(val id: Long,
                                   val type: TransactionTypeViewData,
                                   val date: LocalDate,
                                   val amount: Double)

fun Transaction.toItemViewData() = when(this)
{
	is Transaction.Expense -> TransactionItemViewData(id, TransactionTypeViewData.EXPENSE, date, amount)
	is Transaction.Income -> TransactionItemViewData(id, TransactionTypeViewData.INCOME, date, amount)
}
