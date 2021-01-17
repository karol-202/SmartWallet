package pl.karol202.smartwallet.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.presentation.viewdata.TransactionEditViewData
import pl.karol202.smartwallet.presentation.viewdata.TransactionTypeViewData

interface TransactionEditViewModel : ViewModel
{
	val editedTransaction: Flow<TransactionEditViewData?>
	val finishEvent: Flow<Unit>

	fun editNewTransaction()

	fun editExistingTransaction(transactionId: String)

	fun setTransactionType(type: TransactionTypeViewData)

	fun setTransaction(transaction: TransactionEditViewData)

	fun apply()
}
