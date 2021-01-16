package pl.karol202.smartwallet.presentation.viewmodel

import kotlinx.coroutines.flow.StateFlow
import pl.karol202.smartwallet.presentation.viewdata.TransactionItemViewData

interface TransactionsViewModel : ViewModel
{
	val allTransactions: StateFlow<List<TransactionItemViewData>>
}
