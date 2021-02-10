package pl.karol202.smartwallet.presentation.viewmodel.transactionedit

import kotlinx.coroutines.flow.*
import pl.karol202.smartwallet.domain.entity.Account
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.interactors.usecases.account.GetAccountsFlowUseCase
import pl.karol202.smartwallet.interactors.usecases.account.GetDefaultAccountUseCase
import pl.karol202.smartwallet.interactors.usecases.category.CategoryWithSubcategories
import pl.karol202.smartwallet.interactors.usecases.category.GetCategoriesWithSubcategoriesFlowUseCase
import pl.karol202.smartwallet.interactors.usecases.category.filterByTransactionType
import pl.karol202.smartwallet.interactors.usecases.subcategory.GetOthersSubcategoryUseCase
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
                                   getCategoriesWithSubcategoriesFlowUseCase: GetCategoriesWithSubcategoriesFlowUseCase,
                                   private val getOthersSubcategoryUseCase: GetOthersSubcategoryUseCase,
                                   private val getDefaultAccountUseCase: GetDefaultAccountUseCase,
                                   getAccountsFlowUseCase: GetAccountsFlowUseCase) :
		BaseViewModel(), TransactionEditViewModel
{
	sealed class EditState
	{
		object Idle : EditState()
		{
			override val transaction: TransactionEditViewData? = null
			override val transactionAsEntity: Transaction<*>? = null

			override fun withTransaction(transaction: TransactionEditViewData) = this
		}

		data class New(override val transaction: TransactionEditViewData) : EditState()
		{
			override val transactionAsEntity = transaction.toEntity()

			override fun withTransaction(transaction: TransactionEditViewData) = copy(transaction = transaction)
		}

		data class Existing(val id: String,
		                    override val transaction: TransactionEditViewData) : EditState()
		{
			override val transactionAsEntity = transaction.toEntity(id)

			override fun withTransaction(transaction: TransactionEditViewData) = copy(transaction = transaction)
		}

		abstract val transaction: TransactionEditViewData?
		abstract val transactionAsEntity: Transaction<*>?

		abstract fun withTransaction(transaction: TransactionEditViewData): EditState
	}

	private val editState = MutableStateFlow<EditState>(EditState.Idle)
	override val editedTransaction = editState.map { it.transaction }
	override val availableCategories = editState
			.flatMapLatest { editState ->
				getCategoriesWithSubcategoriesFlowUseCase {
					editState.transactionAsEntity?.let(this::filterByTransactionType)
				}
			}
			.map { it.map(CategoryWithSubcategories::toItemViewData) }
	override val availableAccounts = getAccountsFlowUseCase().map { it.map(Account<Existing>::toItemViewData) }
	override val finishEvent = MutableSharedFlow<Unit>()

	override fun editNewTransaction() = launch {
		val subcategoryId = getOthersSubcategoryUseCase(Category.Type.EXPENSE).id.value
		val accountId = getDefaultAccountUseCase()?.id?.value ?: error("Default account does not exist")
		editState.value = EditState.New(TransactionEditViewData.Expense(subcategoryId, LocalDate.now(), accountId, 0.0))
	}

	override fun editExistingTransaction(transactionId: String) = launch {
		editState.value = EditState.Existing(
			id = transactionId,
			transaction = getTransactionUseCase(transactionId)?.toEditViewData() ?: error("Transaction does not exist")
		)
	}

	override fun setTransactionType(type: TransactionTypeViewData) = launch {
		val current = editState.value.transaction ?: return@launch
		val newSubcategoryId = getOthersSubcategoryUseCase(type.toCategoryTypeEntity()).id.value
		setTransaction(when(type)
		{
			TransactionTypeViewData.EXPENSE -> current.toExpense()
			TransactionTypeViewData.INCOME -> current.toIncome()
		}.withSubcategoryId(newSubcategoryId))
	}

	override fun setTransaction(transaction: TransactionEditViewData)
	{
		editState.value = editState.value.withTransaction(transaction)
	}

	override fun apply() = launch {
		when(val editState = editState.value)
		{
			is EditState.New -> addTransactionUseCase(editState.transactionAsEntity)
			is EditState.Existing -> updateTransactionUseCase(editState.transactionAsEntity)
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
