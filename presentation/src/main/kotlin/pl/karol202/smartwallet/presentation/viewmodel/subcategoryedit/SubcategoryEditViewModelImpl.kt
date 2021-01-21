package pl.karol202.smartwallet.presentation.viewmodel.subcategoryedit

import kotlinx.coroutines.flow.*
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.interactors.usecases.category.GetCategoriesFlowUseCase
import pl.karol202.smartwallet.interactors.usecases.subcategory.*
import pl.karol202.smartwallet.presentation.viewdata.*
import pl.karol202.smartwallet.presentation.viewmodel.BaseViewModel

class SubcategoryEditViewModelImpl(private val getSubcategoryUseCase: GetSubcategoryUseCase,
                                   private val addSubcategoryUseCase: AddSubcategoryUseCase,
                                   private val updateSubcategoryUseCase: UpdateSubcategoryUseCase,
                                   private val removeSubcategoryUseCase: RemoveSubcategoryUseCase,
                                   getCategoriesFlowUseCase: GetCategoriesFlowUseCase) :
		BaseViewModel(), SubcategoryEditViewModel
{
	sealed class EditState
	{
		object Idle : EditState()
		{
			override val subcategory: SubcategoryEditViewData? = null

			override fun withSubcategory(subcategory: SubcategoryEditViewData) = this
		}

		data class New(override val subcategory: SubcategoryEditViewData) : EditState()
		{
			override fun withSubcategory(subcategory: SubcategoryEditViewData) = copy(subcategory = subcategory)
		}

		data class Existing(val id: String,
		                    override val subcategory: SubcategoryEditViewData) : EditState()
		{
			override fun withSubcategory(subcategory: SubcategoryEditViewData) = copy(subcategory = subcategory)
		}

		abstract val subcategory: SubcategoryEditViewData?

		abstract fun withSubcategory(subcategory: SubcategoryEditViewData): EditState
	}

	override val allCategories = getCategoriesFlowUseCase()
			.map { it.map(Category<Existing>::toItemViewData) }

	private val editState = MutableStateFlow<EditState>(EditState.Idle)
	override val editedSubcategory = editState.map { it.subcategory }
	override val finishEvent = MutableSharedFlow<Unit>()

	override fun editNewSubcategory(categoryId: String)
	{
		editState.value = EditState.New(SubcategoryEditViewData(categoryId, ""))
	}

	override fun editExistingSubcategory(subcategoryId: String) = launch {
		editState.value = EditState.Existing(
			id = subcategoryId,
			subcategory = getSubcategoryUseCase(subcategoryId)?.toEditViewData() ?: error("Subcategory does not exist"),
		)
	}

	override fun setSubcategory(subcategory: SubcategoryEditViewData)
	{
		editState.value = editState.value.withSubcategory(subcategory)
	}

	override fun apply() = launch {
		when(val editState = editState.value)
		{
			is EditState.New -> addSubcategoryUseCase(editState.subcategory.toEntity())
			is EditState.Existing -> updateSubcategoryUseCase(editState.subcategory.toEntity(editState.id))
		}
		cancel()
	}

	override fun removeSubcategory() = launch {
		val existingEditState = editState.value as? EditState.Existing ?: return@launch
		removeSubcategoryUseCase(existingEditState.id)
		cancel()
	}

	private suspend fun cancel()
	{
		editState.value = EditState.Idle
		finishEvent.emit(Unit)
	}
}
