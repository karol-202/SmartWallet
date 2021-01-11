package pl.karol202.smartwallet.presentation.viewmodel

import kotlinx.coroutines.flow.StateFlow
import pl.karol202.smartwallet.presentation.viewdata.TransactionViewData

interface TransactionsViewModel : ViewModel
{
	val allTransactions: StateFlow<List<TransactionViewData>>
}
