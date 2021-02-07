package pl.karol202.smartwallet.presentation.viewmodel.subcategoryedit

import kotlinx.coroutines.flow.*
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.interactors.usecases.category.GetCategoriesFlowUseCase
import pl.karol202.smartwallet.interactors.usecases.category.GetCategoryUseCase
import pl.karol202.smartwallet.interactors.usecases.category.filterByType
import pl.karol202.smartwallet.interactors.usecases.subcategory.*
import pl.karol202.smartwallet.presentation.viewdata.*
import pl.karol202.smartwallet.presentation.viewmodel.BaseViewModel

class SubcategoryEditViewModelImpl(private val getCategoryUseCase: GetCategoryUseCase,
                                   private val getSubcategoryUseCase: GetSubcategoryUseCase,
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
			override val categoryType: Category.Type? = null
			override val isCategoryChangeable = false
			override val isRemovable = false

			override fun withSubcategory(subcategory: SubcategoryEditViewData) = this
		}

		data class New(override val subcategory: SubcategoryEditViewData) : EditState()
		{
			override val categoryType: Category.Type? = null
			override val isCategoryChangeable = true
			override val isRemovable = false

			override fun withSubcategory(subcategory: SubcategoryEditViewData) = copy(subcategory = subcategory)
		}

		data class Existing(val id: String,
		                    override val subcategory: SubcategoryEditViewData,
		                    override val categoryType: Category.Type,
		                    override val isCategoryChangeable: Boolean,
		                    override val isRemovable: Boolean) : EditState()
		{
			override fun withSubcategory(subcategory: SubcategoryEditViewData) = copy(subcategory = subcategory)
		}

		abstract val subcategory: SubcategoryEditViewData?
		abstract val categoryType: Category.Type?
		abstract val isCategoryChangeable: Boolean
		abstract val isRemovable: Boolean

		abstract fun withSubcategory(subcategory: SubcategoryEditViewData): EditState
	}

	private val editState = MutableStateFlow<EditState>(EditState.Idle)
	override val editedSubcategory = editState.map { it.subcategory }
	override val availableCategories = editState
			.flatMapLatest { editState ->
				getCategoriesFlowUseCase { editState.categoryType?.let(this::filterByType) }
			}
			.map { it.map(Category<Existing>::toItemViewData) }
	override val isCategoryChangeable = editState.map { it.isCategoryChangeable }
	override val isRemovable = editState.map { it.isRemovable }
	override val finishEvent = MutableSharedFlow<Unit>()

	override fun editNewSubcategory(categoryId: String)
	{
		editState.value = EditState.New(subcategory = SubcategoryEditViewData(categoryId, ""))
	}

	override fun editExistingSubcategory(subcategoryId: String) = launch {
		val subcategory = getSubcategoryUseCase(subcategoryId) ?: error("Subcategory does not exist")
		val category = getCategoryUseCase(subcategory.categoryId) ?: error("Category does not exist")
		editState.value = EditState.Existing(
			id = subcategoryId,
			subcategory = subcategory.toEditViewData(),
			categoryType = category.type,
			isCategoryChangeable = !subcategory.isOthers,
			isRemovable = !subcategory.isOthers
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
		val existingEditState = editState.value as? EditState.Existing
		if(existingEditState == null || !existingEditState.isRemovable) return@launch
		removeSubcategoryUseCase(existingEditState.id)
		cancel()
	}

	private suspend fun cancel()
	{
		editState.value = EditState.Idle
		finishEvent.emit(Unit)
	}
}
