package pl.karol202.smartwallet.presentation.viewmodel.impl

import kotlinx.coroutines.flow.MutableStateFlow
import pl.karol202.smartwallet.interactors.usecases.transactions.GetTransactionUseCase
import pl.karol202.smartwallet.presentation.viewdata.TransactionEditViewData
import pl.karol202.smartwallet.presentation.viewdata.TransactionTypeViewData
import pl.karol202.smartwallet.presentation.viewdata.toEditViewData
import pl.karol202.smartwallet.presentation.viewmodel.TransactionEditViewModel

class TransactionEditViewModelImpl(private val getTransactionUseCase: GetTransactionUseCase) :
		BaseViewModel(), TransactionEditViewModel
{
	override val editedTransaction = MutableStateFlow<TransactionEditViewData?>(null)

	override fun editNewTransaction()
	{
		editedTransaction.value = TransactionEditViewData.Expense(0.0)
	}

	override fun editExistingTransaction(transactionId: Long) = launch {
		editedTransaction.value = getTransactionUseCase(transactionId).toEditViewData()
	}

	override fun setTransactionType(type: TransactionTypeViewData)
	{
		val current = editedTransaction.value ?: return
		editedTransaction.value = when(type)
		{
			TransactionTypeViewData.EXPENSE -> current.toExpense()
			TransactionTypeViewData.INCOME -> current.toIncome()
		}
	}

	override fun setTransaction(transaction: TransactionEditViewData)
	{
		editedTransaction.value = transaction
	}
}
