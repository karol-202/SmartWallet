package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Transaction
import java.time.LocalDate

data class TransactionItemViewData(val id: String,
                                   val type: TransactionTypeViewData,
                                   val date: LocalDate,
                                   val amount: Double)

fun Transaction<Existing>.toItemViewData() = when(this)
{
	is Transaction.Expense -> TransactionItemViewData(id.value, TransactionTypeViewData.EXPENSE, date, amount)
	is Transaction.Income -> TransactionItemViewData(id.value, TransactionTypeViewData.INCOME, date, amount)
}
