package pl.karol202.smartwallet.presentation.viewmodel.transactionedit

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.presentation.viewdata.AccountItemViewData
import pl.karol202.smartwallet.presentation.viewdata.CategoryWithSubcategoriesItemViewData
import pl.karol202.smartwallet.presentation.viewdata.TransactionEditViewData
import pl.karol202.smartwallet.presentation.viewdata.TransactionTypeViewData
import pl.karol202.smartwallet.presentation.viewmodel.ViewModel

interface TransactionEditViewModel : ViewModel
{
	val editedTransaction: Flow<TransactionEditViewData?>
	val availableCategories: Flow<List<CategoryWithSubcategoriesItemViewData>>
	val availableAccounts: Flow<List<AccountItemViewData>>
	val finishEvent: Flow<Unit>

	fun editNewTransaction()

	fun editExistingTransaction(transactionId: String)

	fun setTransactionType(type: TransactionTypeViewData)

	fun setTransaction(transaction: TransactionEditViewData)

	fun apply()

	fun removeTransaction()
}
