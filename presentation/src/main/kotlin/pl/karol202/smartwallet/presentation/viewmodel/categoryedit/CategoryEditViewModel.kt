package pl.karol202.smartwallet.presentation.viewmodel.categoryedit

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.presentation.viewdata.CategoryEditViewData
import pl.karol202.smartwallet.presentation.viewdata.SubcategoryItemViewData
import pl.karol202.smartwallet.presentation.viewmodel.ViewModel

interface CategoryEditViewModel : ViewModel
{
	val editedCategory: Flow<CategoryEditViewData?>
	val subcategories: Flow<List<SubcategoryItemViewData>?>
	val finishEvent: Flow<Unit>

	fun editNewCategory()

	fun editExistingCategory(categoryId: String)

	fun setCategory(category: CategoryEditViewData)

	fun apply()

	fun removeCategory()
}
