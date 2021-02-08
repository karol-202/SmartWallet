package pl.karol202.smartwallet.presentation.viewmodel.accountsedit

import kotlinx.coroutines.flow.*
import pl.karol202.smartwallet.interactors.usecases.account.AddAccountUseCase
import pl.karol202.smartwallet.interactors.usecases.account.GetAccountUseCase
import pl.karol202.smartwallet.interactors.usecases.account.RemoveAccountUseCase
import pl.karol202.smartwallet.interactors.usecases.account.UpdateAccountUseCase
import pl.karol202.smartwallet.presentation.viewdata.*
import pl.karol202.smartwallet.presentation.viewmodel.BaseViewModel

class AccountEditViewModelImpl(private val getAccountUseCase: GetAccountUseCase,
                               private val addAccountUseCase: AddAccountUseCase,
                               private val updateAccountUseCase: UpdateAccountUseCase,
                               private val removeAccountUseCase: RemoveAccountUseCase) :
		BaseViewModel(), AccountEditViewModel
{
	sealed class EditState
	{
		object Idle : EditState()
		{
			override val id: String? = null
			override val account: AccountEditViewData? = null

			override fun withAccount(account: AccountEditViewData) = this
		}

		data class New(override val account: AccountEditViewData) : EditState()
		{
			override val id: String? = null

			override fun withAccount(account: AccountEditViewData) = copy(account = account)
		}

		data class Existing(override val id: String,
		                    override val account: AccountEditViewData) : EditState()
		{
			override fun withAccount(account: AccountEditViewData) = copy(account = account)
		}

		abstract val id: String?
		abstract val account: AccountEditViewData?

		abstract fun withAccount(account: AccountEditViewData): EditState
	}

	private val editState = MutableStateFlow<EditState>(EditState.Idle)
	override val editedAccount = editState.map { it.account }
	override val finishEvent = MutableSharedFlow<Unit>()

	override fun editNewAccount()
	{
		editState.value = EditState.New(account = AccountEditViewData(name = ""))
	}

	override fun editExistingAccount(accountId: String) = launch {
		val account = getAccountUseCase(accountId) ?: error("Account does not exist")
		editState.value = EditState.Existing(id = accountId, account = account.toEditViewData())
	}

	override fun setAccount(account: AccountEditViewData)
	{
		editState.value = editState.value.withAccount(account)
	}

	override fun apply() = launch {
		when(val editState = editState.value)
		{
			is EditState.New -> addAccountUseCase(editState.account.toEntity())
			is EditState.Existing -> updateAccountUseCase(editState.account.toEntity(id = editState.id))
		}
		cancel()
	}

	override fun removeAccount() = launch {
		val existingEditState = editState.value as? EditState.Existing ?: return@launch
		removeAccountUseCase(existingEditState.id)
		cancel()
	}

	private suspend fun cancel()
	{
		editState.value = EditState.Idle
		finishEvent.emit(Unit)
	}
}
