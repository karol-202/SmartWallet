package pl.karol202.smartwallet.presentation.viewmodel.transactions

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.presentation.viewdata.CategoryWithSubcategoriesItemViewData
import pl.karol202.smartwallet.presentation.viewdata.TransactionItemViewData
import pl.karol202.smartwallet.presentation.viewmodel.ViewModel

interface TransactionsViewModel : ViewModel
{
	val allTransactions: Flow<List<TransactionItemViewData>>
}
