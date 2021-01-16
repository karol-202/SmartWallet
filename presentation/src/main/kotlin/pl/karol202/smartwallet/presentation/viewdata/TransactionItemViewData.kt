package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Transaction

data class TransactionItemViewData(val id: Long,
                                   val type: TransactionTypeViewData,
                                   val amount: Double)

fun Transaction.toItemViewData() = when(this)
{
	is Transaction.Expense -> TransactionItemViewData(id, TransactionTypeViewData.EXPENSE, amount)
	is Transaction.Income -> TransactionItemViewData(id, TransactionTypeViewData.INCOME, amount)
}
