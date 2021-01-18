package pl.karol202.smartwallet.presentation.viewmodel

import kotlinx.coroutines.flow.StateFlow
import pl.karol202.smartwallet.presentation.viewdata.CategoryItemViewData

interface CategoriesViewModel : ViewModel
{
	val allCategories: StateFlow<List<CategoryItemViewData>>
}
