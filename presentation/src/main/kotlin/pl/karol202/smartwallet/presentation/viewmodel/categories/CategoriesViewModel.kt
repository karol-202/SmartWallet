package pl.karol202.smartwallet.presentation.viewmodel.categories

import kotlinx.coroutines.flow.StateFlow
import pl.karol202.smartwallet.presentation.viewdata.CategoryItemViewData
import pl.karol202.smartwallet.presentation.viewmodel.ViewModel

interface CategoriesViewModel : ViewModel
{
	val allCategories: StateFlow<List<CategoryItemViewData>>
}
