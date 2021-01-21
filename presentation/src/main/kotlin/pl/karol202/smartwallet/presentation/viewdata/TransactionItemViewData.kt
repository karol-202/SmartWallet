package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.domain.entity.Transaction
import java.time.LocalDate

data class TransactionItemViewData(val id: String,
                                   val type: TransactionTypeViewData,
                                   val category: CategoryItemViewData,
                                   val subcategory: SubcategoryItemViewData,
                                   val date: LocalDate,
                                   val amount: Double)

fun Transaction<Existing>.toItemViewData(category: Category<Existing>, subcategory: Subcategory<Existing>) = when(this)
{
	is Transaction.Expense -> TransactionItemViewData(id.value, TransactionTypeViewData.EXPENSE,
	                                                  category.toItemViewData(), subcategory.toItemViewData(), date, amount)
	is Transaction.Income -> TransactionItemViewData(id.value, TransactionTypeViewData.INCOME,
	                                                 category.toItemViewData(), subcategory.toItemViewData(), date, amount)
}
