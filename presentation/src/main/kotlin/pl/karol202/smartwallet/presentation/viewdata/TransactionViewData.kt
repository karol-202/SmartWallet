package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.presentation.viewdata.TransactionViewData.Type

data class TransactionViewData(val id: Long,
                               val type: Type,
                               val amount: Double)
{
	enum class Type
	{
		EXPENSE, INCOME
	}
}

fun Transaction.toViewData() = when(this)
{
	is Transaction.Expense -> TransactionViewData(id, Type.EXPENSE, amount)
	is Transaction.Income -> TransactionViewData(id, Type.INCOME, amount)
}
