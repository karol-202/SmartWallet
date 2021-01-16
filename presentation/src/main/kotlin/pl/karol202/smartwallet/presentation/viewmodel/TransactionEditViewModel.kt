package pl.karol202.smartwallet.presentation.viewmodel

import kotlinx.coroutines.flow.StateFlow
import pl.karol202.smartwallet.presentation.viewdata.TransactionEditViewData
import pl.karol202.smartwallet.presentation.viewdata.TransactionTypeViewData

interface TransactionEditViewModel : ViewModel
{
	val editedTransaction: StateFlow<TransactionEditViewData?>

	fun editNewTransaction()

	fun editExistingTransaction(transactionId: Long)

	fun setTransactionType(type: TransactionTypeViewData)

	fun setTransaction(transaction: TransactionEditViewData)
}
