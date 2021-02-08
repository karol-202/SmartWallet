package pl.karol202.smartwallet.presentation.viewmodel.subcategoryedit

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.presentation.viewdata.CategoryItemViewData
import pl.karol202.smartwallet.presentation.viewdata.SubcategoryEditViewData
import pl.karol202.smartwallet.presentation.viewmodel.ViewModel

interface SubcategoryEditViewModel : ViewModel
{
	enum class TransactionsRemovePolicy
	{
		REMOVE, MOVE_TO_OTHERS
	}

	val editedSubcategory: Flow<SubcategoryEditViewData?>
	val availableCategories: Flow<List<CategoryItemViewData>>
	val isCategoryChangeable: Flow<Boolean>
	val isRemovable: Flow<Boolean>
	val finishEvent: Flow<Unit>

	fun editNewSubcategory(categoryId: String)

	fun editExistingSubcategory(subcategoryId: String)

	fun setSubcategory(subcategory: SubcategoryEditViewData)

	fun apply()

	fun removeSubcategory(transactionsPolicy: TransactionsRemovePolicy)
}
