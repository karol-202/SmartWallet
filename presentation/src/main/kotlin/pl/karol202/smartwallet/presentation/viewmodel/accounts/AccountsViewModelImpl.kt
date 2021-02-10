package pl.karol202.smartwallet.presentation.viewmodel.accounts

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.interactors.usecases.account.AccountWithDefault
import pl.karol202.smartwallet.interactors.usecases.account.GetAccountsWithDefaultFlowUseCase
import pl.karol202.smartwallet.interactors.usecases.account.MarkAccountAsDefaultUseCase
import pl.karol202.smartwallet.presentation.viewdata.toItemViewData
import pl.karol202.smartwallet.presentation.viewmodel.BaseViewModel

class AccountsViewModelImpl(getAccountsWithDefaultFlowUseCase: GetAccountsWithDefaultFlowUseCase,
                            private val markAccountAsDefaultUseCase: MarkAccountAsDefaultUseCase) : BaseViewModel(),
                                                                                                    AccountsViewModel
{
	override val allAccounts = getAccountsWithDefaultFlowUseCase()
			.map { it.map(AccountWithDefault::toItemViewData) }

	override fun markAccountAsDefault(accountId: String) = launch { markAccountAsDefaultUseCase(accountId) }
}
