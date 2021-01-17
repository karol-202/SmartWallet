package pl.karol202.smartwallet.presentation.viewmodel.impl

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.interactors.usecases.transactions.GetTransactionsFlowUseCase
import pl.karol202.smartwallet.presentation.viewdata.toItemViewData
import pl.karol202.smartwallet.presentation.viewmodel.TransactionsViewModel

class TransactionsViewModelImpl(getTransactionsFlowUseCase: GetTransactionsFlowUseCase) :
		BaseViewModel(), TransactionsViewModel
{
	override val allTransactions = getTransactionsFlowUseCase()
			.map { it.map(Transaction<Existing>::toItemViewData) }
			.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}
