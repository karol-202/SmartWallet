package pl.karol202.smartwallet.presentation.viewmodel.accounts

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.presentation.viewdata.AccountItemViewData
import pl.karol202.smartwallet.presentation.viewmodel.ViewModel

interface AccountsViewModel : ViewModel
{
	val allAccounts: Flow<List<AccountItemViewData>>
}
