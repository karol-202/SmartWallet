package pl.karol202.smartwallet.presentation.viewmodel.accountsedit

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.presentation.viewdata.AccountEditViewData
import pl.karol202.smartwallet.presentation.viewmodel.ViewModel

interface AccountEditViewModel : ViewModel
{
	enum class RemovalCapability
	{
		REMOVABLE,
		DEFAULT_ACCOUNT
	}

	val editedAccount: Flow<AccountEditViewData?>
	val isDefault: Flow<Boolean>
	val removalCapability: Flow<RemovalCapability>
	val finishEvent: Flow<Unit>

	fun editNewAccount()

	fun editExistingAccount(accountId: String)

	fun setAccount(account: AccountEditViewData)

	fun apply()

	fun removeAccount()
}
