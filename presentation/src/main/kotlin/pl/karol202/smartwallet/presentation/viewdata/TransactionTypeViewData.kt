package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Category

enum class TransactionTypeViewData
{
	EXPENSE, INCOME
}

fun TransactionTypeViewData.toCategoryTypeEntity() = when(this)
{
	TransactionTypeViewData.EXPENSE -> Category.Type.EXPENSE
	TransactionTypeViewData.INCOME -> Category.Type.INCOME
}
