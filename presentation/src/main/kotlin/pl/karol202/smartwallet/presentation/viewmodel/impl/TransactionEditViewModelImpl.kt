package pl.karol202.smartwallet.presentation.viewmodel.impl

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.interactors.usecases.transaction.AddTransactionUseCase
import pl.karol202.smartwallet.interactors.usecases.transaction.GetTransactionUseCase
import pl.karol202.smartwallet.interactors.usecases.transaction.UpdateTransactionUseCase
import pl.karol202.smartwallet.presentation.viewdata.TransactionEditViewData
import pl.karol202.smartwallet.presentation.viewdata.TransactionTypeViewData
import pl.karol202.smartwallet.presentation.viewdata.toEditViewData
import pl.karol202.smartwallet.presentation.viewdata.toEntity
import pl.karol202.smartwallet.presentation.viewmodel.TransactionEditViewModel
import java.time.LocalDate

class TransactionEditViewModelImpl(private val getTransactionUseCase: GetTransactionUseCase,
                                   private val addTransactionUseCase: AddTransactionUseCase,
                                   private val updateTransactionUseCase: UpdateTransactionUseCase) :
		BaseViewModel(), TransactionEditViewModel
{
	sealed class EditState
	{
		object Idle : EditState()
		{
			override val transaction: TransactionEditViewData? = null

			override fun withTransaction(transaction: TransactionEditViewData) = this
		}

		data class New(override val transaction: TransactionEditViewData) : EditState()
		{
			override fun withTransaction(transaction: TransactionEditViewData) = copy(transaction = transaction)
		}

		data class Existing(val id: String,
		                    override val transaction: TransactionEditViewData) : EditState()
		{
			override fun withTransaction(transaction: TransactionEditViewData) = copy(transaction = transaction)
		}

		abstract val transaction: TransactionEditViewData?

		abstract fun withTransaction(transaction: TransactionEditViewData): EditState
	}

	private val editState = MutableStateFlow<EditState>(EditState.Idle)
	override val editedTransaction = editState.map { it.transaction }
	override val finishEvent = MutableSharedFlow<Unit>()

	@Suppress("NewApi") // Not relevant as it is not an Android module
	override fun editNewTransaction()
	{
		editState.value = EditState.New(TransactionEditViewData.Expense(LocalDate.now(), 0.0))
	}

	override fun editExistingTransaction(transactionId: String) = launch {
		editState.value = EditState.Existing(transactionId, getTransactionUseCase(transactionId).toEditViewData())
	}

	override fun setTransactionType(type: TransactionTypeViewData)
	{
		val current = editState.value.transaction ?: return
		setTransaction(when(type)
		{
			TransactionTypeViewData.EXPENSE -> current.toExpense()
			TransactionTypeViewData.INCOME -> current.toIncome()
		})
	}

	override fun setTransaction(transaction: TransactionEditViewData)
	{
		editState.value = editState.value.withTransaction(transaction)
	}

	override fun apply() = launch {
		when(val editState = editState.value)
		{
			is EditState.New -> addTransactionUseCase(editState.transaction.toEntity())
			is EditState.Existing -> updateTransactionUseCase(editState.transaction.toEntity(editState.id))
		}
		editState.value = EditState.Idle
		finishEvent.emit(Unit)
	}
}
