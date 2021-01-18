package pl.karol202.smartwallet.presentation.viewmodel.transactions

import kotlinx.coroutines.flow.StateFlow
import pl.karol202.smartwallet.presentation.viewdata.TransactionItemViewData
import pl.karol202.smartwallet.presentation.viewmodel.ViewModel

interface TransactionsViewModel : ViewModel
{
	val allTransactions: StateFlow<List<TransactionItemViewData>>
}
