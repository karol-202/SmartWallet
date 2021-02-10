package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.interactors.usecases.transaction.TransactionWithAllData
import java.time.LocalDate

sealed class TransactionItemViewData
{
	data class Expense(override val id: String,
	                   override val type: TransactionTypeViewData,
	                   override val category: CategoryItemViewData,
	                   override val subcategory: SubcategoryItemViewData,
	                   override val date: LocalDate,
	                   val account: AccountItemViewData,
	                   val amount: Double) : TransactionItemViewData()

	data class Income(override val id: String,
	                  override val type: TransactionTypeViewData,
	                  override val category: CategoryItemViewData,
	                  override val subcategory: SubcategoryItemViewData,
	                  override val date: LocalDate,
	                  val account: AccountItemViewData,
	                  val amount: Double) : TransactionItemViewData()

	abstract val id: String
	abstract val type: TransactionTypeViewData
	abstract val category: CategoryItemViewData
	abstract val subcategory: SubcategoryItemViewData
	abstract val date: LocalDate
}

fun TransactionWithAllData.toItemViewData() = with(transaction) {
	when(this)
	{
		is Transaction.Expense -> TransactionItemViewData.Expense(id.value, TransactionTypeViewData.EXPENSE,
		                                                          category.toItemViewData(), subcategory.toItemViewData(),
		                                                          date, account.toItemViewData(), amount)
		is Transaction.Income -> TransactionItemViewData.Income(id.value, TransactionTypeViewData.INCOME,
		                                                        category.toItemViewData(), subcategory.toItemViewData(),
		                                                        date, account.toItemViewData(), amount)
	}
}
