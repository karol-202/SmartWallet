package pl.karol202.smartwallet.presentation.viewmodel.transactions

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.interactors.usecases.transaction.GetTransactionsWithAllDataFlowUseCase
import pl.karol202.smartwallet.interactors.usecases.transaction.TransactionWithAllData
import pl.karol202.smartwallet.presentation.viewdata.toItemViewData
import pl.karol202.smartwallet.presentation.viewmodel.BaseViewModel

class TransactionsViewModelImpl(getTransactionsWithAllDataFlowUseCase: GetTransactionsWithAllDataFlowUseCase) :
		BaseViewModel(), TransactionsViewModel
{
	override val allTransactions = getTransactionsWithAllDataFlowUseCase()
			.map { it.map(TransactionWithAllData::toItemViewData) }
}
