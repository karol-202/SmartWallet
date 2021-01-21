package pl.karol202.smartwallet.presentation.viewmodel.transactions

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.interactors.usecases.category.GetCategoriesWithSubcategoriesFlowUseCase
import pl.karol202.smartwallet.interactors.usecases.transaction.GetTransactionsWithCategoriesFlowUseCase
import pl.karol202.smartwallet.presentation.viewdata.toItemViewData
import pl.karol202.smartwallet.presentation.viewmodel.BaseViewModel

class TransactionsViewModelImpl(getTransactionsWithCategoriesFlowUseCase: GetTransactionsWithCategoriesFlowUseCase) :
		BaseViewModel(), TransactionsViewModel
{
	override val allTransactions = getTransactionsWithCategoriesFlowUseCase()
			.map { it.map { (transaction, category, subcategory) -> transaction.toItemViewData(category, subcategory) } }
}
