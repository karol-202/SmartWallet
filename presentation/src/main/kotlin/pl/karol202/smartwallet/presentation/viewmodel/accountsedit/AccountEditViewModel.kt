package pl.karol202.smartwallet.presentation.viewmodel.accountsedit

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.presentation.viewdata.AccountEditViewData
import pl.karol202.smartwallet.presentation.viewmodel.ViewModel

interface AccountEditViewModel : ViewModel
{
	val editedAccount: Flow<AccountEditViewData?>
	val finishEvent: Flow<Unit>

	fun editNewAccount()

	fun editExistingAccount(accountId: String)

	fun setAccount(account: AccountEditViewData)

	fun apply()

	fun removeAccount()
}
