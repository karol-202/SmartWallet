package pl.karol202.smartwallet.presentation.viewmodel.categoryedit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.interactors.usecases.category.AddCategoryUseCase
import pl.karol202.smartwallet.interactors.usecases.category.GetCategoryUseCase
import pl.karol202.smartwallet.interactors.usecases.category.UpdateCategoryUseCase
import pl.karol202.smartwallet.interactors.usecases.subcategory.GetSubcategoriesOfCategoryUseCase
import pl.karol202.smartwallet.presentation.viewdata.*
import pl.karol202.smartwallet.presentation.viewmodel.BaseViewModel

class CategoryEditViewModelImpl(private val getCategoryUseCase: GetCategoryUseCase,
                                private val addCategoryUseCase: AddCategoryUseCase,
                                private val updateCategoryUseCase: UpdateCategoryUseCase,
                                private val getSubcategoriesOfCategoryUseCase: GetSubcategoriesOfCategoryUseCase) :
		BaseViewModel(), CategoryEditViewModel
{
	sealed class EditState
	{
		object Idle : EditState()
		{
			override val category: CategoryEditViewData? = null
			override val subcategories: List<SubcategoryItemViewData>? = null

			override fun withCategory(category: CategoryEditViewData) = this
		}

		data class New(override val category: CategoryEditViewData) : EditState()
		{
			override val subcategories: List<SubcategoryItemViewData>? = null

			override fun withCategory(category: CategoryEditViewData) = copy(category = category)
		}

		data class Existing(val id: String,
		                    override val category: CategoryEditViewData,
		                    override val subcategories: List<SubcategoryItemViewData>) : EditState()
		{
			override fun withCategory(category: CategoryEditViewData) = copy(category = category)
		}

		abstract val category: CategoryEditViewData?
		abstract val subcategories: List<SubcategoryItemViewData>?

		abstract fun withCategory(category: CategoryEditViewData): EditState
	}

	private val editState = MutableStateFlow<EditState>(EditState.Idle)
	override val editedCategory = editState.map { it.category }
	override val subcategories = editState.map { it.subcategories }
	override val finishEvent = MutableSharedFlow<Unit>()

	@Suppress("NewApi") // Not relevant as it is not an Android module
	override fun editNewCategory()
	{
		editState.value = EditState.New(CategoryEditViewData("", CategoryTypeViewData.EXPENSE))
	}

	override fun editExistingCategory(categoryId: String) = launch {
		editState.value = EditState.Existing(
			id = categoryId,
			category = getCategoryUseCase(categoryId).toEditViewData(),
			subcategories = getSubcategoriesOfCategoryUseCase(categoryId).map { it.toItemViewData() }
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
		editState.value = EditState.Idle
		finishEvent.emit(Unit)
	}
}
