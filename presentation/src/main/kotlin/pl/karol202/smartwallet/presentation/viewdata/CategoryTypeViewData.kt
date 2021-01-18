package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Category

enum class CategoryTypeViewData
{
	EXPENSE, INCOME
}

fun Category.Type.toViewData() = when(this)
{
	Category.Type.EXPENSE -> CategoryTypeViewData.EXPENSE
	Category.Type.INCOME -> CategoryTypeViewData.INCOME
}

fun CategoryTypeViewData.toEntity() = when(this)
{
	CategoryTypeViewData.EXPENSE -> Category.Type.EXPENSE
	CategoryTypeViewData.INCOME -> Category.Type.INCOME
}
