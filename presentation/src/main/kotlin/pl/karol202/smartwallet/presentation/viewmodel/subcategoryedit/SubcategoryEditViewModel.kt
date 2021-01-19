package pl.karol202.smartwallet.presentation.viewmodel.subcategoryedit

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.presentation.viewdata.CategoryEditViewData
import pl.karol202.smartwallet.presentation.viewdata.SubcategoryEditViewData
import pl.karol202.smartwallet.presentation.viewdata.SubcategoryItemViewData
import pl.karol202.smartwallet.presentation.viewmodel.ViewModel

interface SubcategoryEditViewModel : ViewModel
{
	val editedSubcategory: Flow<SubcategoryEditViewData?>
	val finishEvent: Flow<Unit>

	fun editNewSubcategory(categoryId: String)

	fun editExistingSubcategory(subcategoryId: String)

	fun setSubcategory(subcategory: SubcategoryEditViewData)

	fun apply()
}
