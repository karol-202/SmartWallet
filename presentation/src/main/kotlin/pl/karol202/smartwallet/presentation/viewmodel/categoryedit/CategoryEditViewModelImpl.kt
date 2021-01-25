package pl.karol202.smartwallet.presentation.viewmodel.categoryedit

import kotlinx.coroutines.flow.*
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.interactors.usecases.category.AddCategoryUseCase
import pl.karol202.smartwallet.interactors.usecases.category.GetCategoryUseCase
import pl.karol202.smartwallet.interactors.usecases.category.RemoveCategoryUseCase
import pl.karol202.smartwallet.interactors.usecases.category.UpdateCategoryUseCase
import pl.karol202.smartwallet.interactors.usecases.subcategory.GetSubcategoriesFlowUseCase
import pl.karol202.smartwallet.interactors.usecases.subcategory.filterByCategoryId
import pl.karol202.smartwallet.presentation.viewdata.*
import pl.karol202.smartwallet.presentation.viewmodel.BaseViewModel

class CategoryEditViewModelImpl(private val getCategoryUseCase: GetCategoryUseCase,
                                private val addCategoryUseCase: AddCategoryUseCase,
                                private val updateCategoryUseCase: UpdateCategoryUseCase,
                                private val removeCategoryUseCase: RemoveCategoryUseCase,
                                getSubcategoriesFlowUseCase: GetSubcategoriesFlowUseCase) :
		BaseViewModel(), CategoryEditViewModel
{
	sealed class EditState
	{
		object Idle : EditState()
		{
			override val id: String? = null
			override val category: CategoryEditViewData? = null

			override fun withCategory(category: CategoryEditViewData) = this
		}

		data class New(override val category: CategoryEditViewData) : EditState()
		{
			override val id: String? = null

			override fun withCategory(category: CategoryEditViewData) = copy(category = category)
		}

		data class Existing(override val id: String,
		                    override val category: CategoryEditViewData) : EditState()
		{
			override fun withCategory(category: CategoryEditViewData) = copy(category = category)
		}

		abstract val id: String?
		abstract val category: CategoryEditViewData?

		abstract fun withCategory(category: CategoryEditViewData): EditState
	}

	private val editState = MutableStateFlow<EditState>(EditState.Idle)
	override val editedCategory = editState.map { it.category }
	override val subcategories = editState
			.flatMapLatest { editState ->
				editState.id?.let { categoryId -> getSubcategoriesFlowUseCase { filterByCategoryId(categoryId) } }
						?: flowOf(null)
			}
			.map { it?.map(Subcategory<Existing>::toItemViewData) }
	override val finishEvent = MutableSharedFlow<Unit>()

	override fun editNewCategory()
	{
		editState.value = EditState.New(category = CategoryEditViewData("", CategoryTypeViewData.EXPENSE))
	}

	override fun editExistingCategory(categoryId: String) = launch {
		editState.value = EditState.Existing(
			id = categoryId,
			category = getCategoryUseCase(categoryId)?.toEditViewData() ?: error("Category does not exist")
		)
	}

	override fun setCategory(category: CategoryEditViewData)
	{
		editState.value = editState.value.withCategory(category)
	}

	override fun apply() = launch {
		when(val editState = editState.value)
		{
			is EditState.New -> addCategoryUseCase(editState.category.toEntity())
			is EditState.Existing -> updateCategoryUseCase(editState.category.toEntity(editState.id))
		}
		cancel()
	}

	override fun removeCategory() = launch {
		val existingEditState = editState.value as? EditState.Existing ?: return@launch
		removeCategoryUseCase(existingEditState.id)
		cancel()
	}

	private suspend fun cancel()
	{
		editState.value = EditState.Idle
		finishEvent.emit(Unit)
	}
}
