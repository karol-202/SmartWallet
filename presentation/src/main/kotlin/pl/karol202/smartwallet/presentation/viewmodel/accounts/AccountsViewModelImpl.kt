package pl.karol202.smartwallet.presentation.viewmodel.accounts

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.domain.entity.Account
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.interactors.usecases.account.GetAccountsFlowUseCase
import pl.karol202.smartwallet.presentation.viewdata.toItemViewData
import pl.karol202.smartwallet.presentation.viewmodel.BaseViewModel

class AccountsViewModelImpl(getAccountsFlowUseCase: GetAccountsFlowUseCase) : BaseViewModel(), AccountsViewModel
{
	override val allAccounts = getAccountsFlowUseCase()
			.map { it.map(Account<Existing>::toItemViewData) }
}
