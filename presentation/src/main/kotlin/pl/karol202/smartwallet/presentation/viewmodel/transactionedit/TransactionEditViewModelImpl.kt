package pl.karol202.smartwallet.presentation.viewmodel.transactionedit

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.interactors.usecases.category.GetCategoriesWithSubcategoriesFlowUseCase
import pl.karol202.smartwallet.interactors.usecases.transaction.AddTransactionUseCase
import pl.karol202.smartwallet.interactors.usecases.transaction.GetTransactionUseCase
import pl.karol202.smartwallet.interactors.usecases.transaction.RemoveTransactionUseCase
import pl.karol202.smartwallet.interactors.usecases.transaction.UpdateTransactionUseCase
import pl.karol202.smartwallet.presentation.viewdata.*
import pl.karol202.smartwallet.presentation.viewmodel.BaseViewModel
import java.time.LocalDate

class TransactionEditViewModelImpl(private val getTransactionUseCase: GetTransactionUseCase,
                                   private val addTransactionUseCase: AddTransactionUseCase,
                                   private val updateTransactionUseCase: UpdateTransactionUseCase,
                                   private val removeTransactionUseCase: RemoveTransactionUseCase,
                                   getCategoriesWithSubcategoriesFlowUseCase: GetCategoriesWithSubcategoriesFlowUseCase) :
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

	override val allCategories = getCategoriesWithSubcategoriesFlowUseCase()
			.map { it.map { (category, subcategories) -> category.toItemViewData(subcategories) } }

	private val editState = MutableStateFlow<EditState>(EditState.Idle)
	override val editedTransaction = editState.map { it.transaction }
	override val finishEvent = MutableSharedFlow<Unit>()

	override fun editNewTransaction() = launch {
		// TODO Change to Others subcategory as soon as it's introduced
		// Requires at least one category and one subcategory to exist in order to function
		val subcategoryId = allCategories.first().first().subcategories.first().id
		editState.value = EditState.New(TransactionEditViewData.Expense(subcategoryId, LocalDate.now(), 0.0))
	}

	override fun editExistingTransaction(transactionId: String) = launch {
		editState.value = EditState.Existing(
			id = transactionId,
			transaction = getTransactionUseCase(transactionId)?.toEditViewData() ?: error("Transaction does not exist")
		)
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
		cancel()
	}

	override fun removeTransaction() = launch {
		val existingEditState = editState.value as? EditState.Existing ?: return@launch
		removeTransactionUseCase(existingEditState.id)
		cancel()
	}

	private suspend fun cancel()
	{
		editState.value = EditState.Idle
		finishEvent.emit(Unit)
	}
}
